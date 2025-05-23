package band.effective.office.tablet.ui.fastEvent.store

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import band.effective.office.tablet.domain.useCase.SelectRoomUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.ui.fastEvent.FastEventComponent
import band.effective.office.tablet.utils.BootstrapperTimer
import band.effective.office.tablet.utils.removeSeconds
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

class FastEventStoreFactory(
    private val storeFactory: StoreFactory,
    private val navigate: (FastEventComponent.ModalConfig) -> Unit,
    private val selectedRoom: RoomInfo,
    private val rooms: List<RoomInfo>,
    private val onEventCreation: suspend (EventInfo, String) -> Either<ErrorResponse, EventInfo>,
    private val onRemoveEvent: suspend (EventInfo, String) -> Either<ErrorResponse, String>,
    private val minEventDuration: Int,
    private val onCloseRequest: () -> Unit,
) : KoinComponent {
    val selectRoomUseCase: SelectRoomUseCase by inject()
    private val timerUseCase: TimerUseCase by inject()
    private val currentTimeTimer = BootstrapperTimer<Action>(timerUseCase)

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FastEventStore =
        object : FastEventStore,
            Store<FastEventStore.Intent, FastEventStore.State, Nothing> by storeFactory.create(
                name = "FastEventStore",
                initialState = FastEventStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        val selectRoom: RoomInfo? = selectRoomUseCase.getRoom(
                            currentRoom = selectedRoom,
                            rooms = rooms,
                            minEventDuration = minEventDuration
                        )

                        if (selectRoom != null) {
                            dispatch(Action.CreateEvent(selectRoom.name, minEventDuration))
                            return@launch
                        }

                        dispatch(Action.FastBooking(isSuccess = false))
                        val nearestFreeRoom = selectRoomUseCase.getNearestFreeRoom(rooms, minEventDuration)

                        dispatch(Action.UntilFreeRoom(minDuration = nearestFreeRoom.second))
                        navigate(FastEventComponent.ModalConfig.FailureModal(nearestFreeRoom.first.name))
                    }

                    dispatch(Action.RefreshTime)
                    currentTimeTimer.start(this, 1.minutes) {
                        withContext(Dispatchers.Main) {
                            dispatch(Action.RefreshTime)
                        }
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        object Success : Message
        object Load : Message
        object Fail : Message
        data class UpdateEvent(val event: EventInfo): Message
        data class UpdateTime(val newTime: Date) : Message
        data class UntilFinish(val minutes: Int) : Message
    }

    sealed interface Action {
        data class CreateEvent(val room: String, val minDuration: Int) : Action

        data class FastBooking(val isSuccess: Boolean) : Action
        data class UpdateEvent(val event: EventInfo) : Action
        object RefreshTime : Action
        data class UntilFreeRoom(val minDuration: Long) : Action
    }

    private inner class ExecutorImpl() :
            CoroutineExecutor<FastEventStore.Intent, Action, FastEventStore.State, Message, Nothing>() {

        override fun executeIntent(
            intent: FastEventStore.Intent,
            getState: () -> FastEventStore.State
        ) {
            when (intent) {
                is FastEventStore.Intent.OnFreeSelectRequest -> freeRoom(state = getState(), intent.room)
                is FastEventStore.Intent.OnCloseWindowRequest -> onCloseRequest()

            }
        }

        private fun freeRoom(state: FastEventStore.State, room: String) = scope.launch() {
            dispatch(Message.Load)
            if (onRemoveEvent(state.event, room ) is Either.Success) {
                onCloseRequest()
            } else dispatch(Message.Fail)
        }

        override fun executeAction(action: Action, getState: () -> FastEventStore.State) {
            when (action) {
                is Action.FastBooking -> {
                    if (action.isSuccess) {
                        dispatch(Message.Success)
                    } else dispatch(Message.Fail)
                }
                is Action.UpdateEvent -> {
                    dispatch(Message.UpdateEvent(event = action.event))
                }
                is Action.RefreshTime -> dispatch(Message.UpdateTime(GregorianCalendar().time))
                is Action.UntilFreeRoom -> {
                    dispatch(
                        Message.UntilFinish(action.minDuration.milliseconds.inWholeMinutes.toInt())
                    )
                }
                is Action.CreateEvent -> createEvent(
                    room = action.room,
                    minDuration = action.minDuration
                )
            }
        }

        private fun createEvent(room: String, minDuration: Int) = scope.launch {
            val eventInfo = EventInfo.emptyEvent.copy(
                startTime = GregorianCalendar().removeSeconds(),
                finishTime = GregorianCalendar().apply {
                    add(
                        Calendar.MINUTE,
                        minDuration
                    )
                }.removeSeconds()
            )
            when (val result = onEventCreation(eventInfo, room)) {
                is Either.Success -> {
                    dispatch(
                        Message.UpdateEvent(
                            event = eventInfo.copy(
                                id = result.data.id
                            )
                        )
                    )
                    dispatch(Message.Success)
                    navigate(FastEventComponent.ModalConfig.SuccessModal(room, eventInfo))
                }
                is Either.Error -> {
                    dispatch(Message.Fail)
                    navigate(FastEventComponent.ModalConfig.FailureModal(room))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<FastEventStore.State, Message> {
        override fun FastEventStore.State.reduce(msg: Message) =
            when(msg) {
                is Message.Load -> copy(isLoad = true)
                is Message.Success -> copy(isLoad = false, isSuccess = true)
                is Message.Fail -> copy(isSuccess = false)
                is Message.UpdateEvent -> copy(event = msg.event)
                is Message.UpdateTime -> copy(currentTime = msg.newTime)
                is Message.UntilFinish -> copy(minutesLeft = msg.minutes)
            }
    }
}