package band.effective.office.tablet.ui.updateEvent.store

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.OrganizersInfoUseCase
import band.effective.office.tablet.ui.updateEvent.UpdateEventComponent
import band.effective.office.tablet.utils.removeSeconds
import band.effective.office.tablet.utils.unbox
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar

class UpdateEventStoreFactory(
    private val storeFactory: StoreFactory,
    private val onCloseRequest: () -> Unit,
    private val onEventCreation: (EventInfo) -> Unit,
    private val onEventUpdate: (EventInfo) -> Unit,
    private val navigate: (UpdateEventComponent.ModalConfig) -> Unit,
    private val room: String,
    private val onDelete: (Slot) -> Unit,
) : KoinComponent {

    val organizersInfoUseCase: OrganizersInfoUseCase by inject()
    val checkBookingUseCase: CheckBookingUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(defaultValue: UpdateEventStore.State = UpdateEventStore.State.defaultValue): UpdateEventStore =
        object : UpdateEventStore,
            Store<UpdateEventStore.Intent, UpdateEventStore.State, UpdateEventStore.Label> by storeFactory.create(
                name = "UpdateEventStore",
                initialState = defaultValue,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(
                            Action.LoadOrganizers(
                                organizersInfoUseCase()
                                    .unbox({
                                        it.saveData ?: listOf()
                                    })
                            )
                        )
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        data class LoadOrganizers(val orgList: List<Organizer>) : Message
        data class ExpandedChange(val newValue: Boolean) : Message
        data class UpdateInformation(
            val newDate: Calendar,
            val newDuration: Int,
            val newOrganizer: Organizer,
        ) : Message

        data class EnableButton(val isEnable: Boolean) : Message
        data class BusyEvent(val isBusy: Boolean) : Message
        data class InputError(val isError: Boolean) : Message
        data class Input(val newInput: String, val newList: List<Organizer>) : Message
        data class UpdateOrganizer(val newValue: Organizer) : Message
        data class ChangeShowSelectDateModal(val newValue: Boolean) : Message
    }

    private sealed interface Action {
        data class LoadOrganizers(val orgList: List<Organizer>) : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<UpdateEventStore.Intent, Action, UpdateEventStore.State, Message, UpdateEventStore.Label>() {
        override fun executeIntent(
            intent: UpdateEventStore.Intent,
            getState: () -> UpdateEventStore.State
        ) {
            val state = getState()
            when (intent) {
                is UpdateEventStore.Intent.OnDeleteEvent -> cancel(state)
                is UpdateEventStore.Intent.OnExpandedChange -> dispatch(Message.ExpandedChange(!state.expanded))
                is UpdateEventStore.Intent.OnSelectOrganizer -> {
                    dispatch(
                        Message.UpdateOrganizer(
                            intent.newOrganizer
                        ),
                    )
                    dispatch(Message.InputError(isError = false))
                    checkEnableButton(inputError = false, state.isBusyEvent)
                }

                is UpdateEventStore.Intent.OnUpdateDate -> {
                    updateInfo(
                        state = state,
                        changeData = intent.updateInDays
                    )
                }

                is UpdateEventStore.Intent.OnUpdateEvent -> {
                    updateEvent(state)
                }
                is UpdateEventStore.Intent.OnUpdateLength -> {
                    updateInfo(
                        state = state,
                        changeDuration = intent.update
                    )
                }

                is UpdateEventStore.Intent.OnDoneInput -> {
                    onDone(state)
                }
                is UpdateEventStore.Intent.OnInput -> {
                    onInput(intent.input, state)
                }
                is UpdateEventStore.Intent.OnCloseSelectDateDialog -> {
                    dispatch(
                        Message.ChangeShowSelectDateModal(false)
                    )
                }

                is UpdateEventStore.Intent.OnOpenSelectDateDialog -> {
                    dispatch(
                        Message.ChangeShowSelectDateModal(true)
                    )
                }

                is UpdateEventStore.Intent.OnSetDate -> {
                    setDay(
                        state = state,
                        newDate = intent.calendar
                    )
                }

                UpdateEventStore.Intent.OnClose -> onCloseRequest()
                UpdateEventStore.Intent.OnBooking -> {
                    createEvent(state)
                }
            }
        }

        fun createEvent(state: UpdateEventStore.State) {
            val event = state.toEventInfo()
            onEventCreation(event)
            onCloseRequest()
        }

        fun setDay(
            state: UpdateEventStore.State,
            newDate: Calendar
        ) = scope.launch {
            val busyEvent: List<EventInfo> = checkBookingUseCase.busyEvents(
                event = state.copy(
                    date = newDate
                ).toEvent(),
                room = room
            ).filter { it.startTime != state.date }
            dispatch(
                Message.UpdateInformation(
                    newDate = newDate,
                    newDuration = state.duration,
                    newOrganizer = state.selectOrganizer,
                )
            )
            dispatch(Message.BusyEvent(isBusy = busyEvent.isNotEmpty()))
            if (state.selectOrganizer != Organizer.default) {
                checkEnableButton(
                    inputError = state.isInputError,
                    busyEvent = busyEvent.isNotEmpty()
                )
            }
        }

        fun cancel(state: UpdateEventStore.State) {
            onDelete(state.event.toSlot())
            onCloseRequest()
        }

        private fun EventInfo.toSlot(): Slot {
            return Slot.EventSlot(start = startTime, finish = finishTime, eventInfo = this)
        }

        fun onDone(state: UpdateEventStore.State) {
            val input = state.inputText.lowercase()
            val organizer =
                state.selectOrganizers.firstOrNull { it.fullName.lowercase().contains(input) }
                    ?: state.event.organizer
            val wrongOrganizer = !state.organizers.contains(organizer)
            dispatch(Message.UpdateOrganizer(organizer))
            dispatch(Message.InputError(wrongOrganizer))
            checkEnableButton(
                inputError = wrongOrganizer,
                busyEvent = state.isBusyEvent
            )
        }

        fun onInput(input: String, state: UpdateEventStore.State) {
            val newList = state.organizers
                .filter { it.fullName.lowercase().contains(input.lowercase()) }
                .sortedBy { it.fullName.lowercase().indexOf(input.lowercase()) }
            dispatch(Message.Input(input, newList))
        }

        fun updateEvent(state: UpdateEventStore.State) {
            onEventUpdate(state.toEventInfo())
            onCloseRequest()
        }

        fun updateInfo(
            state: UpdateEventStore.State,
            changeData: Int = 0,
            changeDuration: Int = 0,
            newOrg: Organizer = state.selectOrganizer
        ) = scope.launch {
            val newDate =
                (state.date.clone() as Calendar).apply { add(Calendar.DAY_OF_WEEK, changeData) }
            val newDuration = state.duration + changeDuration
            val newOrganizer = state.organizers.firstOrNull { it.fullName == newOrg.fullName }
                ?: state.event.organizer
            val busyEvent: List<EventInfo> = checkBookingUseCase.busyEvents(
                event = state.copy(
                    date = newDate,
                    duration = newDuration,
                    selectOrganizer = newOrganizer
                ).toEvent(),
                room = room
            ).filter { it.startTime != state.date }
            if (newDuration > 0 && newDate > today()) {
                dispatch(
                    Message.UpdateInformation(
                        newDate = newDate,
                        newDuration = newDuration,
                        newOrganizer = newOrganizer,
                    )
                )
                dispatch(Message.BusyEvent(busyEvent.isNotEmpty()))
                checkEnableButton(
                    inputError = !state.organizers.contains(newOrganizer),
                    busyEvent = busyEvent.isNotEmpty()
                )
            }
        }

        private fun checkEnableButton(
            inputError: Boolean,
            busyEvent: Boolean
        ){
            if(!inputError && !busyEvent) {
                dispatch(Message.EnableButton(isEnable = true))
            }
            else {
                dispatch(Message.EnableButton(isEnable = false))
            }

        }

        private fun today() = GregorianCalendar().apply {
            add(
                /* field = */ Calendar.MINUTE,
                /* amount = */ -(get(Calendar.HOUR) * 60 + get(Calendar.MINUTE))
            )
        }

        private fun UpdateEventStore.State.toEventInfo(): EventInfo =
            EventInfo(
                startTime = date.removeSeconds(),
                finishTime = (date.clone() as Calendar)
                    .apply { add(Calendar.MINUTE, duration) }
                    .removeSeconds(),
                organizer = selectOrganizer,
                id = event.id,
                isLoading = false,
            )


        override fun executeAction(action: Action, getState: () -> UpdateEventStore.State) {
            when (action) {
                is Action.LoadOrganizers -> dispatch(Message.LoadOrganizers(action.orgList))
            }
        }
    }

    private object ReducerImpl : Reducer<UpdateEventStore.State, Message> {
        override fun UpdateEventStore.State.reduce(msg: Message): UpdateEventStore.State =
            when (msg) {
                is Message.LoadOrganizers -> copy(
                    organizers = msg.orgList,
                    selectOrganizers = msg.orgList
                )
                is Message.ExpandedChange -> copy(expanded = msg.newValue)
                is Message.UpdateInformation -> copy(
                    date = msg.newDate,
                    duration = msg.newDuration,
                    selectOrganizer = msg.newOrganizer,
                    event = msg.event(event.id)
                )
                is Message.InputError -> copy(isInputError = msg.isError)
                is Message.Input -> copy(inputText = msg.newInput, selectOrganizers = msg.newList)
                is Message.UpdateOrganizer -> copy(
                    selectOrganizer = msg.newValue,
                    inputText = msg.newValue.fullName,
                )
                is Message.ChangeShowSelectDateModal -> copy(showSelectDate = msg.newValue)
                is Message.EnableButton -> copy(enableUpdateButton = msg.isEnable)
                is Message.BusyEvent -> copy(isBusyEvent = msg.isBusy)
            }

        private fun Message.UpdateInformation.event(id: String): EventInfo {
            return EventInfo(
                startTime = newDate.clone() as Calendar,
                finishTime = (newDate.clone() as Calendar).apply {
                    add(
                        Calendar.MINUTE,
                        newDuration
                    )
                },
                organizer = newOrganizer,
                id = id,
                isLoading = false,
            )
        }
    }
}

private fun UpdateEventStore.State.toEvent(): EventInfo = EventInfo(
    startTime = date,
    finishTime = (date.clone() as Calendar).apply { add(Calendar.MINUTE, duration) },
    organizer = selectOrganizer,
    id = "",
    isLoading = false,
)
