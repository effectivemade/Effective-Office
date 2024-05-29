package band.effective.office.tablet.ui.mainScreen.settingsComponents.store

import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.domain.useCase.SetRoomUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val setRoomUseCase: SetRoomUseCase by inject()
    private val checkSettingsUseCase: CheckSettingsUseCase by inject()
    private val roomUseCase: RoomInfoUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SettingsStore =
        object : SettingsStore,
            Store<SettingsStore.Intent, SettingsStore.State, Nothing> by storeFactory.create(
                name = "SettingsStore",
                initialState = SettingsStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    dispatch(Action.UpdateCurrentNameRoom(checkSettingsUseCase()))

                    launch {
                        roomUseCase.updateCache()
                        val rooms = roomUseCase.getRoomsNames()
                        println("rooms = ${rooms}")
                        dispatch(Action.Loaded(rooms))
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class UpdateCurrentNameRoom(val nameRoom: String) : Action
        data class Loaded(val rooms: List<String>) : Action
    }


    private sealed interface Message {
        data class ChangeCurrentNameRoom(val nameRoom: String) : Message
        data class UpdateRooms(val rooms: List<String>) : Message
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<SettingsStore.Intent, Action, SettingsStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: SettingsStore.Intent,
            getState: () -> SettingsStore.State
        ) {
            when (intent) {
                is SettingsStore.Intent.OnExitApp -> {}
                is SettingsStore.Intent.ChangeCurrentNameRoom -> {
                    dispatch(Message.ChangeCurrentNameRoom(intent.nameRoom))
                }

                is SettingsStore.Intent.SaveData -> {
                    setRoomUseCase(getState().currentName)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> SettingsStore.State) {
            when (action) {
                is Action.UpdateCurrentNameRoom ->
                    dispatch(Message.ChangeCurrentNameRoom(action.nameRoom))

                is Action.Loaded -> dispatch(Message.UpdateRooms(action.rooms))
            }
        }
    }

    private object ReducerImpl : Reducer<SettingsStore.State, Message> {
        override fun SettingsStore.State.reduce(message: Message): SettingsStore.State =
            when (message) {
                is Message.ChangeCurrentNameRoom -> copy(currentName = message.nameRoom)
                is Message.UpdateRooms -> copy(rooms = message.rooms, loading = false)
            }
    }
}