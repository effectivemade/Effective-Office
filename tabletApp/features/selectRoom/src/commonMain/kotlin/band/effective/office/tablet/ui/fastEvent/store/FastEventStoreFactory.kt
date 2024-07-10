package band.effective.office.tablet.ui.fastEvent.store

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.useCase.BookingUseCase
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import band.effective.office.tablet.domain.useCase.CancelUseCase
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.ui.fastEvent.FastEventComponent
import band.effective.office.tablet.utils.BootstrapperTimer
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
import java.util.Date
import java.util.GregorianCalendar
import kotlin.time.Duration.Companion.minutes

class FastEventStoreFactory(
    private val storeFactory: StoreFactory,
    private val navigate: (FastEventComponent.ModalConfig) -> Unit,
    private val room: String,
    private val eventInfo: EventInfo,
    private val onCloseRequest: () -> Unit,
) : KoinComponent {
    val cancelUseCase: CancelUseCase by inject()
    val checkBookingUseCase: CheckBookingUseCase by inject()
    val bookingUseCase: BookingUseCase by inject()
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
                        val busyEvents = (checkBookingUseCase.busyEvents(
                            event = eventInfo,
                            room = room
                        ) as? Either.Success?)?.data!!

                        if(busyEvents.isEmpty())
                        {
                            val result = bookingUseCase.invoke(
                                eventInfo = eventInfo,
                                room = room
                            )
                            when (result) {
                                is Either.Success -> {
                                    dispatch(Action.UpdateEvent(eventInfo.copy(id = result.data)))
                                    dispatch(Action.FastBooking(true))
                                    navigate(FastEventComponent.ModalConfig.SuccessModal)
                                }
                                is Either.Error -> {
                                    dispatch(Action.FastBooking(false))
                                    navigate(FastEventComponent.ModalConfig.FailureModal)
                                }
                            }
                        }
                        else {
                            dispatch(Action.FastBooking(false))
                            dispatch(Action.GetDifferenceInTime(
                                endTime = busyEvents[0].finishTime.timeInMillis ,
                                startTime = GregorianCalendar().timeInMillis
                            ))
                            navigate(FastEventComponent.ModalConfig.FailureModal)
                        }
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
        data class FastBooking(val isSuccess: Boolean) : Action
        data class UpdateEvent(val event: EventInfo) : Action
        object RefreshTime : Action
        data class GetDifferenceInTime(val endTime: Long, val startTime: Long) : Action
    }

    private inner class ExecutorImpl() :
            CoroutineExecutor<FastEventStore.Intent, Action, FastEventStore.State, Message, Nothing>() {

        override fun executeIntent(
            intent: FastEventStore.Intent,
            getState: () -> FastEventStore.State
        ) {
            when (intent) {
                is FastEventStore.Intent.OnFreeSelectRequest -> freeRoom(getState())
                is FastEventStore.Intent.OnCloseWindowRequest -> onCloseRequest()

            }
        }

        private fun freeRoom(state: FastEventStore.State) = scope.launch() {
            dispatch(Message.Load)
            if (cancelUseCase(state.event) is Either.Success) {
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
                    dispatch(Message.UpdateEvent(action.event))
                }
                is Action.RefreshTime -> dispatch(Message.UpdateTime(GregorianCalendar().time))
                is Action.GetDifferenceInTime -> {
                    dispatch(
                        Message.UntilFinish(((action.endTime - action.startTime) / (1000 * 60)).toInt())
                    )
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