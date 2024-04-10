package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.entity.BookingInteract
import band.effective.office.elevator.ui.booking.models.MockDataSpaces
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.booking.models.sheetData.SelectedBookingPeriodState
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store.BookPeriodStore
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookingStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val bookingInteract: BookingInteract by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore,
            Store<BookingStore.Intent, BookingStore.State, BookingStore.Label> by storeFactory.create(
                name = "BookingStore",
                initialState = BookingStore.State.initState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(Action.InitWorkSpaces)
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Msg {

        data class UpdateAllZones(val zones: List<WorkspaceZoneUI>) : Msg

        data class SelectedTypeList(val type: TypesList) : Msg

        data class ChangeSelectedWorkSpacesZone(val workSpacesZone: List<WorkspaceZoneUI>) : Msg

        data class ChangeWorkSpacesUI(val workSpacesUI: List<WorkSpaceUI>) : Msg

        data class UpdateSelectedWorkspace(val workspaceId: String, val seatName: String) : Msg

        data class ChangeLoadingWorkspace(val isLoading: Boolean) : Msg

        data class UpdateSelectedBookingPeriodState(val selectedSate: SelectedBookingPeriodState) :
            Msg

        data class UpdateAllWorkspaceList(val workspaces: List<WorkSpaceUI>) : Msg
    }

    private sealed interface Action {
        data object InitWorkSpaces : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<BookingStore.Intent, Action, BookingStore.State, Msg, BookingStore.Label>() {
        override fun executeIntent(
            intent: BookingStore.Intent,
            getState: () -> BookingStore.State
        ) {
            when (intent) {
                is BookingStore.Intent.ShowPlace -> dispatch(
                    Msg.SelectedTypeList(
                        type = TypesList(
                            name = MainRes.strings.app_name,
                            icon = MainRes.images.table_icon,
                            type = WorkSpaceType.WORK_PLACE
                        )
                    )
                )

                is BookingStore.Intent.OpenChooseZone -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenChooseZone)
                    }
                }

                is BookingStore.Intent.OpenBookPeriod -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenBookPeriod)
                    }
                }

                is BookingStore.Intent.OpenBookAccept -> {
                    publish(BookingStore.Label.OpenBookAccept)
                    with(intent.value) {
                        dispatch(
                            Msg.UpdateSelectedWorkspace(
                                workspaceId = workSpaceId,
                                seatName = workSpaceName
                            )
                        )
                    }
                }

                is BookingStore.Intent.ChangeSelectedWorkSpacesZone -> {
                    scope.launch {
                        val state = getState()
                        getSpacesUI(
                            workspaceZoneUI = intent.workspaceZoneUI,
                            selectedStartTime = state.selectedStartTime,
                            selectedFinishDate = state.selectedFinishDate,
                            selectedStartDate = state.selectedStartDate,
                            selectedFinishTime = state.selectedFinishTime,
                            workSpacesType = state.workSpacesType,
                            dispatch = {
                                dispatch(Msg.ChangeWorkSpacesUI(it))
                                dispatch(Msg.ChangeSelectedWorkSpacesZone(intent.workspaceZoneUI))
                            }
                        )
                    }
                }

                is BookingStore.Intent.ChangeSelectedType -> {

                    val zones = when (intent.selectedType.type) {
                        WorkSpaceType.WORK_PLACE -> getState().allZonesList
                        WorkSpaceType.MEETING_ROOM -> MockDataSpaces.allMeetingRooms
                    }
                    scope.launch {
                        val state = getState()
                        getSpacesUI(
                            workspaceZoneUI = zones,
                            selectedStartTime = state.selectedStartTime,
                            selectedFinishDate = state.selectedFinishDate,
                            selectedStartDate = state.selectedStartDate,
                            selectedFinishTime = state.selectedFinishTime,
                            workSpacesType = state.workSpacesType,
                            dispatch = {
                                dispatch(Msg.ChangeSelectedWorkSpacesZone(zones))
                                dispatch(Msg.ChangeWorkSpacesUI(it))
                            }
                        )
                    }
                    dispatch(Msg.SelectedTypeList(type = intent.selectedType))
                }

                is BookingStore.Intent.ApplyBookingPeriodFromSheet -> {
                    val curState = getState()
                    val bookingState = intent.selectedState
                    scope.launch {
                        with(bookingState) {
                            getSpacesUI(
                                workspaceZoneUI = curState.currentWorkspaceZones,
                                selectedStartTime = startTime,
                                selectedFinishDate = finishDate,
                                selectedStartDate = startDate,
                                selectedFinishTime = finishTime,
                                workSpacesType = curState.workSpacesType,
                                dispatch = {
                                    dispatch(Msg.ChangeWorkSpacesUI(it))
                                }
                            )
                        }
                    }
                    dispatch(Msg.UpdateSelectedBookingPeriodState(bookingState))
                }

                is BookingStore.Intent.HandleLabelFromBookingPeriodSheet ->
                    handleLabelFromBookingPeriodSheet(intent.label)
            }
        }

        override fun executeAction(action: Action, getState: () -> BookingStore.State) {
            when (action) {
                Action.InitWorkSpaces -> {
                    scope.launch {
                        val state = getState()
                        getSpacesUI(
                            workspaceZoneUI = state.currentWorkspaceZones,
                            selectedStartTime = state.selectedStartTime,
                            selectedFinishDate = state.selectedFinishDate,
                            selectedStartDate = state.selectedStartDate,
                            selectedFinishTime = state.selectedFinishTime,
                            workSpacesType = state.workSpacesType,
                            dispatch = {
                                dispatch(Msg.UpdateAllWorkspaceList(it))
                                dispatch(Msg.ChangeWorkSpacesUI(it))
                            }
                        )
                    }
                    scope.launch {
                        initZones()
                    }
                }
            }
        }

        private fun handleLabelFromBookingPeriodSheet(label: BookPeriodStore.Label) {
            when(label) {
                is BookPeriodStore.Label.ShowToast ->
                    publish(BookingStore.Label.ShowToast(label.message))
            }
        }
        private suspend fun initZones() {
            withContext(Dispatchers.IO) {
                bookingInteract.getZones().collect { zonesResponse ->
                    withContext(Dispatchers.Main) {
                        when (zonesResponse) {
                            is Either.Success -> {
                                val zones: List<WorkspaceZoneUI> = zonesResponse.data
                                dispatch(Msg.UpdateAllZones(zones = zones))
                            }

                            is Either.Error -> {
                                //TODO(Artem Gruzdev) schedule error
                            }
                        }
                    }
                }
            }
        }
        // TODO (Artem Gruzdev):
        //  Надо продумать логику, когда фильтрация броней не будет ходить на бэк за данными
        private fun filerWorkspaceByZones(
            workspaces: List<WorkSpaceUI>,
            zones: List<WorkspaceZoneUI>
        )  = bookingInteract.filterWorkspacesList(workspaces = workspaces, zones = zones)

        private suspend fun getSpacesUI(
            selectedStartTime: LocalTime,
            selectedStartDate: LocalDate,
            workSpacesType: WorkSpaceType,
            selectedFinishDate: LocalDate,
            selectedFinishTime: LocalTime,
            workspaceZoneUI: List<WorkspaceZoneUI>,
            dispatch: (List<WorkSpaceUI>) -> Unit,
        ) {

            dispatch(Msg.ChangeLoadingWorkspace(isLoading = true))

            withContext(Dispatchers.IO) {
                bookingInteract.getWorkspaces(
                    tag = workSpacesType.type,
                    freeFrom = LocalDateTime(
                        date = selectedStartDate,
                        time = selectedStartTime
                    ),
                    freeUntil = LocalDateTime(
                        date = selectedFinishDate,
                        time = selectedFinishTime,
                    ),
                    selectedWorkspacesZone = workspaceZoneUI
                ).collect { response ->
                    withContext(Dispatchers.Main) {
                        when (response) {
                            is Either.Success -> {
                                dispatch(response.data)
                                dispatch(Msg.ChangeLoadingWorkspace(false))
                            }

                            is Either.Error -> {
                                dispatch(listOf())
                                // TODO SHOW ERROR ON UI
                            }
                        }
                    }
                }

            }
        }
    }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            return when (msg) {
                is Msg.ChangeSelectedWorkSpacesZone -> copy(currentWorkspaceZones = msg.workSpacesZone)

                is Msg.SelectedTypeList -> copy(
                    workSpacesType = msg.type.type,
                    selectedType = msg.type
                )

                is Msg.ChangeWorkSpacesUI -> copy(
                    isLoadingListWorkspaces = false,
                    workSpaces = msg.workSpacesUI
                )

                is Msg.UpdateSelectedWorkspace ->
                    copy(
                        selectedWorkspaceId = msg.workspaceId,
                        selectedSeatName = msg.seatName
                    )

                is Msg.ChangeLoadingWorkspace -> copy(isLoadingListWorkspaces = msg.isLoading)

                is Msg.UpdateAllZones -> copy(
                    currentWorkspaceZones = msg.zones,
                    allZonesList = msg.zones
                )

                is Msg.UpdateSelectedBookingPeriodState -> {
                    Napier.d { "set type of end ${msg.selectedSate.endPeriodBookingType}" }

                    copy(
                        selectedStartTime = msg.selectedSate.startTime,
                        selectedStartDate = msg.selectedSate.startDate,
                        selectedFinishTime = msg.selectedSate.finishTime,
                        selectedFinishDate = msg.selectedSate.finishDate,
                        dateOfEndPeriod = msg.selectedSate.dateOfEndPeriod,
                        bookingPeriod = msg.selectedSate.bookingPeriod,
                        typeOfEnd = msg.selectedSate.endPeriodBookingType,
                        repeatBooking = msg.selectedSate.repeatBooking
                    )
                }

                is Msg.UpdateAllWorkspaceList -> copy(workSpacesAll = msg.workspaces)
            }
        }
    }
}