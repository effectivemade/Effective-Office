package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.AboutEmployeeInteractor
import band.effective.office.elevator.expects.makeCall
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.expects.pickTelegram
import band.effective.office.elevator.expects.setClipboardText
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.employee.aboutEmployee.models.toUIAbout
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import band.effective.office.elevator.utils.telegramNickParse
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.commandiron.wheel_picker_compose.utils.Max
import com.commandiron.wheel_picker_compose.utils.Min
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AboutEmployeeStoreFactory(
    private val storeFactory: StoreFactory,
    private val employeeInfo: EmployeeInfo
) : KoinComponent {

    private val aboutEmployeeInteractor: AboutEmployeeInteractor by inject()
    private var currentUser = employeeInfo.toUIAbout()
    private var filtration = BookingsFilter(meetRoom = true, workPlace = true)
    private var datedList = false

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                user = currentUser,
                reservedSeatsList = listOf(),
                beginDate = getCurrentDate(),
                endDate = getCurrentDate(),
                isLoadingBookings = true,
                dateFiltrationOnReserves = datedList,
                filtrationOnReserves = false
            ),
            bootstrapper = coroutineBootstrapper {
                launch {
                    dispatch(Action.FetchUserInfo(employee = employeeInfo.toUIAbout()))
                }
            },
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ProfileData ->
                    copy(
                        user = msg.user,
                        reservedSeatsList = msg.reservedSeatsList,
                        filtrationOnReserves = msg.filtrationOnReserves,
                        isLoading = false
                    )

                is Msg.UpdateSeatsReservation -> {
                    copy(
                        beginDate = msg.beginDate,
                        endDate = msg.endDate,
                        reservedSeatsList = msg.reservedSeatsList,
                        dateFiltrationOnReserves = msg.dateFiltrationOnReserves,
                        filtrationOnReserves = msg.filtrationOnReserves,
                        isLoading = false
                    )
                }
                is Msg.UpdateLoadingBookingState -> {
                    copy(
                        isLoadingBookings = msg.isLoading
                    )
                }
            }
    }

    private sealed interface Action {
        data class FetchUserInfo(val employee: User) : Action
    }

    private sealed interface Msg {
        data class ProfileData(
            val user: User,
            val reservedSeatsList: List<ReservedSeat>,
            val filtrationOnReserves: Boolean
        ) : Msg

        data class UpdateSeatsReservation(
            val beginDate: LocalDate,
            val endDate: LocalDate?,
            val reservedSeatsList: List<ReservedSeat>,
            val dateFiltrationOnReserves: Boolean,
            val filtrationOnReserves: Boolean
        ) : Msg

        data class UpdateLoadingBookingState(val isLoading: Boolean) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.BackClicked -> TODO()
                Intent.TelegramClicked -> pickTelegram(telegramNickParse(getState().user.telegram))
                Intent.TelephoneClicked -> makeCall(getState().user.phoneNumber)
                Intent.TransferMoneyClicked -> pickSBP(getState().user.phoneNumber)

                is Intent.OnClickApplyDate -> {
                    scope.launch {
                        publish(Label.CloseCalendar)
                        datedList = true
                        fetchUserInfoByDate(
                            dates = intent.date,
                            ownerId = currentUser.id,
                            bookingsFilter = filtration
                        )
                    }
                }

                Intent.OpenBottomDialog -> {
                    scope.launch {
                        publish(Label.OpenBottomDialog)
                    }
                }

                is Intent.CloseBottomDialog -> {
                    scope.launch {
                        publish(Label.CloseBottomDialog)
                        dispatch(Msg.UpdateLoadingBookingState(true))
                        intent.bookingsFilter.let { bookingsFilter ->
                            if (datedList) {
                                fetchUserInfoByDate(
                                    dates = listOf(
                                        getState().beginDate,
                                        getState().endDate
                                    )
                                        .mapNotNull { it },
                                    ownerId = currentUser.id,
                                    bookingsFilter = bookingsFilter
                                )
                            } else {
                                fetchUserInfo(
                                    dates = listOf(
                                        getState().beginDate,
                                        getState().endDate
                                    )
                                        .mapNotNull { it },
                                    employee = currentUser,
                                    bookingsFilter = bookingsFilter
                                )
                            }
                        }
                        dispatch(Msg.UpdateLoadingBookingState(false))
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            scope.launch {
                when (action) {
                    is Action.FetchUserInfo -> {
                        dispatch(Msg.UpdateLoadingBookingState(true))
                        fetchUserInfo(
                            dates = listOf(
                                getState().beginDate,
                                getState().endDate
                            )
                                .mapNotNull { it },
                            employee = action.employee,
                            bookingsFilter = filtration
                        )
                        dispatch(Msg.UpdateLoadingBookingState(false))
                    }
                }
            }
        }

        private fun fetchUserInfoByDate(
            dates: List<LocalDate>,
            ownerId: String,
            bookingsFilter: BookingsFilter
        ) {
            if (dates.isEmpty()) return

            val sortedDates = dates.sorted()
            val beginDate = sortedDates.first()
            val endDate = sortedDates.last()

            val beginDateTime = LocalDateTime(date = beginDate, time = LocalTime.Min)
            val endDateTime = LocalDateTime(date = endDate, time = LocalTime.Max)

            filtration = bookingsFilter
            dispatch(Msg.UpdateLoadingBookingState(true))
            scope.launch(Dispatchers.IO) {
                aboutEmployeeInteractor
                    .getBookingsForUser(
                        bookingsFilter = bookingsFilter,
                        ownerId = ownerId,
                        beginDateTime = beginDateTime,
                        endDateTime = endDateTime,
                    )
                    .collect { newList ->
                        withContext(Dispatchers.Main) {
                            when (newList) {
                                is Either.Error -> {
                                    //TODO show error on UI
                                }

                                is Either.Success -> {
                                    dispatch(
                                        Msg.UpdateSeatsReservation(
                                            beginDate = beginDate,
                                            endDate = if (beginDate == endDate) null else endDate,
                                            reservedSeatsList = newList.data,
                                            dateFiltrationOnReserves = datedList,
                                            filtrationOnReserves = !(filtration.workPlace && filtration.meetRoom)
                                        )
                                    )
                                    dispatch(Msg.UpdateLoadingBookingState(false))
                                }
                            }
                        }
                    }
            }
        }

        private fun fetchUserInfo(
            dates: List<LocalDate>,
            employee: User,
            bookingsFilter: BookingsFilter
        ) {
            if (dates.isEmpty()) return

            val beginDate = dates.first()
            val endDate = dates.last()

            val beginDateTime = LocalDateTime(date = beginDate, time = LocalTime.Min)
            val endDateTime = LocalDateTime(date = endDate, time = LocalTime.Max)

            scope.launch(Dispatchers.IO) {
                aboutEmployeeInteractor
                    .getBookingsForUser(
                        bookingsFilter = bookingsFilter,
                        ownerId = employee.id,
                        beginDateTime = beginDateTime,
                        endDateTime = endDateTime,
                    ).collect { newList ->
                        withContext(Dispatchers.Main) {
                            when (newList) {
                                is Either.Error -> {
                                    //TODO show error on UI
                                }
                                is Either.Success -> {
                                    dispatch(
                                        Msg.ProfileData(
                                            user = employee,
                                            reservedSeatsList = newList.data,
                                            filtrationOnReserves = !(filtration.workPlace && filtration.meetRoom)
                                        )
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
}
