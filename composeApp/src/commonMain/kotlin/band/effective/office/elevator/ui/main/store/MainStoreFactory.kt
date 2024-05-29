package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.entity.BookingInteract
import band.effective.office.elevator.domain.useCase.DeleteBookingUseCase
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.commandiron.wheel_picker_compose.utils.Max
import com.commandiron.wheel_picker_compose.utils.Min
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val elevatorUseCase: ElevatorCallUseCase by inject()
    private val deleteBookingUseCase: DeleteBookingUseCase by inject()
    private val bookingInteract: BookingInteract by inject()

    private var filtration = BookingsFilter(meetRoom = true, workPlace = true)

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State(
                    reservedSeats = listOf(),
                    elevatorState = ElevatorState.Below,
                    beginDate = getCurrentDate(),
                    idSelectedBooking = "",
                    isLoading = true,
                    endDate = null,
                    enableCallElevator = true,
                    isRefreshing = false
                ),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(
                            Action.LoadBookings(date = getCurrentDate())
                        )
                    }
                }

            ) {}

    private sealed interface Msg {
        data class UpdateElevatorState(val elevatorState: ElevatorState) : Msg
        data class UpdateSeatsReservation(val reservedSeats: List<ReservedSeat>) : Msg

        data class UpdateBookingSelectedId(val bookingId: String) : Msg

        data class UpdateSeatReservationByDate(
            val beginDate: LocalDate,
            val endDate: LocalDate?,
            val reservedSeats: List<ReservedSeat>,
        ) : Msg

        data class UpdateBookingLoadingState(val isLoading: Boolean) : Msg
        data class UpdateCallElevator(val newValue: Boolean) : Msg

        data class UpdateRefreshing(val newValue: Boolean) : Msg
    }

    private sealed interface Action {
        data class LoadBookings(val date: LocalDate) : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, Action, MainStore.State, Msg, MainStore.Label>() {
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                MainStore.Intent.OnClickCallElevator -> {
                    //doElevatorCall() TODO(Maksim Mishenko) uncomment call elevator
                    // NOTE(Maksim Mishenko) if call success do this:

                    scope.launch {
                        dispatch(Msg.UpdateCallElevator(false))
                        delay(10000L)
                        dispatch(Msg.UpdateCallElevator(true))
                    }
                }

                is MainStore.Intent.OnClickShowOption -> {
                    scope.launch {
                        dispatch(Msg.UpdateBookingSelectedId(bookingId = intent.bookingId))
                        publish(MainStore.Label.ShowOptions)
                    }
                }

                MainStore.Intent.OnClickCloseCalendar -> {
                    scope.launch {
                        publish(MainStore.Label.CloseCalendar)
                    }
                }

                MainStore.Intent.OnClickOpenCalendar -> {
                    scope.launch {
                        publish(MainStore.Label.OpenCalendar)
                    }
                }

                is MainStore.Intent.OnClickApplyDate -> {
                    Napier.d {
                        "Selected dates  ${intent.dates}"
                    }
                    publish(MainStore.Label.CloseCalendar)
                    changeBookingsByDate(dates = intent.dates, bookingsFilter = filtration)
                }

                is MainStore.Intent.OnClickDeleteBooking -> {
                    scope.launch {
                        publish(MainStore.Label.HideOptions)
                    }
                    scope.launch(Dispatchers.IO) {
                        bookingInteract.deleteBooking(getState().idSelectedBooking)
                        changeBookingsByDate(
                            dates = listOf(
                                getState().beginDate,
                                getState().endDate
                            ).mapNotNull { it },
                            bookingsFilter = filtration
                        )
                    }
                }

                MainStore.Intent.OpenFiltersBottomDialog -> {
                    scope.launch {
                        publish(MainStore.Label.OpenFiltersBottomDialog)
                    }
                }

                is MainStore.Intent.CloseFiltersBottomDialog -> {
                    publish(MainStore.Label.CloseFiltersBottomDialog)
                }

                MainStore.Intent.OnClickHideOption -> {
                    scope.launch {
                        publish(MainStore.Label.HideOptions)
                    }
                }

                MainStore.Intent.OnRefreshContent -> {
                    refreshBookings(
                        dates = listOf(
                            getState().beginDate,
                            getState().endDate
                        ).mapNotNull { it },
                        bookingsFilter = filtration
                    )
                }

            }
        }

        private fun deleteBooking(seat: ReservedSeat) {
            scope.launch(Dispatchers.IO) {
                deleteBookingUseCase.deleteBooking(
                    seat = seat,
                    coroutineScope = this
                )
            }
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.LoadBookings -> {

                    val startCurrentWeek = action.date
                    val fridayWeekNumber = 5
                    val countDaysToFriday = fridayWeekNumber - startCurrentWeek.dayOfWeek.ordinal - 1
                    val datePeriodForFriday = DatePeriod(days = countDaysToFriday)
                    val endCurrentWeek = startCurrentWeek.plus(datePeriodForFriday)

                    changeBookingsByDate(
                        dates = listOf(startCurrentWeek, endCurrentWeek),
                        bookingsFilter = filtration
                    )
                }
            }
        }

        private fun doElevatorCall() {
            scope.launch {
                dispatch(Msg.UpdateElevatorState(ElevatorState.Goes))
                delay(1000)
                publish(
                    when (val result = elevatorUseCase.callElevator()) {
                        is ApiResponse.Error.HttpError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                (MainRes.strings.server_error.format(result.code.toString()))
                                        as StringResource
                            )
                        )

                        ApiResponse.Error.NetworkError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                MainRes.strings.network_error
                            )
                        )

                        ApiResponse.Error.SerializationError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                MainRes.strings.developer_error
                            )
                        )

                        ApiResponse.Error.UnknownError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                MainRes.strings.developer_error
                            )
                        )

                        is ApiResponse.Success -> {
                            MainStore.Label.ShowSuccess
                        }
                    }
                )
                delay(1000)
                dispatch(Msg.UpdateElevatorState(ElevatorState.Raised))
            }
        }

        private fun refreshBookings(dates: List<LocalDate>, bookingsFilter: BookingsFilter) {
            if (dates.isEmpty()) return

            val beginDate = dates.first()
            val endDate = dates.last()

            val beginDateTime = LocalDateTime(date = beginDate, time = LocalTime.Min)
            val endDateTime = LocalDateTime(date = endDate, time = LocalTime.Max)

            dispatch(Msg.UpdateRefreshing(true))

            scope.launch(Dispatchers.IO) {
                bookingInteract
                    .getForUser(
                        beginDateTime = beginDateTime,
                        endDateTime = endDateTime,
                        bookingsFilter = bookingsFilter
                    )
                    .collect { bookings ->
                        withContext(Dispatchers.Main) {
                            when (bookings) {
                                is Either.Error -> {
                                    // TODO show error on UI
                                }

                                is Either.Success -> {
                                    dispatch(Msg.UpdateSeatsReservation(reservedSeats = bookings.data))
                                    dispatch(Msg.UpdateRefreshing(false))
                                }
                            }
                        }
                    }
            }
        }
        fun getBookingsForUserByDate(dates: List<LocalDate>, bookingsFilter: BookingsFilter) {

            if (dates.isEmpty()) return

            val beginDate = dates.first()
            val endDate = dates.last()

            val beginDateTime = LocalDateTime(date = beginDate, time = LocalTime.Min)
            val endDateTime = LocalDateTime(date = endDate, time = LocalTime.Max)

            filtration = bookingsFilter

            scope.launch {
                dispatch(Msg.UpdateBookingLoadingState(isLoading = true))
            }

            scope.launch(Dispatchers.IO) {
                bookingInteract
                    .getForUser(
                        beginDateTime = beginDateTime,
                        endDateTime = endDateTime,
                        bookingsFilter = bookingsFilter
                    )
                    .collect { bookings ->
                        withContext(Dispatchers.Main) {
                            when (bookings) {
                                is Either.Error -> {
                                    // TODO show error on UI
                                }

                                is Either.Success -> {
                                    dispatch(Msg.UpdateSeatsReservation(reservedSeats = bookings.data))
                                }
                            }
                        }
                    }
            }
        }

        fun changeBookingsByDate(dates: List<LocalDate>, bookingsFilter: BookingsFilter) {

            if (dates.isEmpty()) return

            val sortedDates = dates.sorted()
            val beginDate = sortedDates.first()
            val endDate = sortedDates.last()

            val beginDateTime = LocalDateTime(date = beginDate, time = LocalTime.Min)
            val endDateTime = LocalDateTime(date = endDate, time = LocalTime.Max)

            filtration = bookingsFilter

            scope.launch {
                dispatch(Msg.UpdateBookingLoadingState(isLoading = true))
            }

            scope.launch {
                withContext(Dispatchers.IO) {
                    bookingInteract
                        .getForUser(
                            beginDateTime = beginDateTime,
                            endDateTime = endDateTime,
                            bookingsFilter = bookingsFilter
                        )
                        .collect { bookings ->
                            withContext(Dispatchers.Main) {
                                when (bookings) {
                                    is Either.Error -> {
                                        // TODO show error on UI
                                    }

                                    is Either.Success -> {

                                        dispatch(
                                            Msg.UpdateSeatReservationByDate(
                                                beginDate = beginDate,
                                                endDate = if (beginDate == endDate) null else endDate,
                                                reservedSeats = bookings.data,
                                            )
                                        )
                                    }
                                }
                                dispatch(Msg.UpdateBookingLoadingState(false))
                            }
                        }
                }
            }
        }

    }

    private object ReducerImpl : Reducer<MainStore.State, Msg> {
        override fun MainStore.State.reduce(message: Msg): MainStore.State =
            when (message) {
                is Msg.UpdateElevatorState -> copy(elevatorState = message.elevatorState)
                is Msg.UpdateSeatsReservation -> copy(
                    isLoading = false,
                    reservedSeats = message.reservedSeats
                )

                is Msg.UpdateSeatReservationByDate -> {
                    Napier.d { "change state" }
                    copy(
                        beginDate = message.beginDate,
                        endDate = message.endDate,
                        reservedSeats = message.reservedSeats,
                        isLoading = false
                    )
                }

                is Msg.UpdateBookingSelectedId -> copy(idSelectedBooking = message.bookingId)
                is Msg.UpdateBookingLoadingState -> copy(isLoading = message.isLoading)
                is Msg.UpdateCallElevator -> copy(enableCallElevator = message.newValue)
                is Msg.UpdateRefreshing -> copy(isRefreshing = message.newValue)
            }
    }
}


