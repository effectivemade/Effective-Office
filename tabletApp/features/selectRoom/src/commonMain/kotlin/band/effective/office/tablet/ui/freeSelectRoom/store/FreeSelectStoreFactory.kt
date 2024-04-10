package band.effective.office.tablet.ui.freeSelectRoom.store

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.useCase.CancelUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FreeSelectStoreFactory(
    private val storeFactory: StoreFactory,
    private val eventInfo: EventInfo
) : KoinComponent {
    val cancelUseCase: CancelUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FreeSelectStore =
        object : FreeSelectStore,
            Store<FreeSelectStore.Intent, FreeSelectStore.State, FreeSelectStore.Label> by storeFactory.create(
                name = "FreeSelectStore",
                initialState = FreeSelectStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {},
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        object Success : Message
        object Load : Message
        object Fail : Message
        object Reset : Message
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<FreeSelectStore.Intent, Nothing, FreeSelectStore.State, Message, FreeSelectStore.Label>() {

        override fun executeIntent(
            intent: FreeSelectStore.Intent,
            getState: () -> FreeSelectStore.State
        ) {
            when (intent) {
                is FreeSelectStore.Intent.OnFreeSelectRequest -> freeRoom()
                is FreeSelectStore.Intent.OnCloseWindowRequest -> {
                    publish(FreeSelectStore.Label.Close)
                    dispatch(Message.Reset)
                }
            }
        }

        private fun freeRoom() = scope.launch() {
            dispatch(Message.Load)
            if (cancelUseCase(eventInfo) is Either.Success) {
                publish(FreeSelectStore.Label.Close)
                dispatch(Message.Success)
                dispatch(Message.Reset)
            } else dispatch(Message.Fail)
        }
    }

    private object ReducerImpl : Reducer<FreeSelectStore.State, Message> {
        override fun FreeSelectStore.State.reduce(msg: Message) =
            when (msg) {
                is Message.Load -> copy(isLoad = true)
                is Message.Success -> copy(isLoad = false)
                is Message.Fail -> copy(isLoad = false, isSuccess = false)
                is Message.Reset -> FreeSelectStore.State.defaultState
            }
    }
}