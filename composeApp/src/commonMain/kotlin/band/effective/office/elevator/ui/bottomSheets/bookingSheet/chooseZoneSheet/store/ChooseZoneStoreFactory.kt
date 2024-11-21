package band.effective.office.elevator.ui.bottomSheets.bookingSheet.chooseZoneSheet.store

import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

class ChooseZoneStoreFactory(
    private val storeFactory: StoreFactory,
    private val close: () -> Unit,
    private val confirm: (List<WorkspaceZoneUI>) -> Unit
) {
    fun create(startList: List<WorkspaceZoneUI>): ChooseZoneStore =
        object : ChooseZoneStore,
            Store<ChooseZoneStore.Intent, ChooseZoneStore.State, Nothing> by storeFactory.create(
                name = "",
                initialState = ChooseZoneStore.State.default.copy(zones = startList),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        data class onZoneClick(val zones: List<WorkspaceZoneUI>) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ChooseZoneStore.Intent, Nothing, ChooseZoneStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: ChooseZoneStore.Intent,
            getState: () -> ChooseZoneStore.State
        ) {
            when (intent) {
                ChooseZoneStore.Intent.onCloseRequest -> close()
                ChooseZoneStore.Intent.onConfirmRequest -> {
                    confirm(getState().zones)
                    close()
                }

                is ChooseZoneStore.Intent.onZoneClick -> dispatch(
                    Message.onZoneClick(
                        updateZones(
                            getState(),
                            intent.zone
                        )
                    )
                )
            }
        }

        private fun updateZones(
            state: ChooseZoneStore.State,
            changedZone: WorkspaceZoneUI
        ): List<WorkspaceZoneUI> =
            state.zones.map { if (changedZone.name == it.name) it.copy(isSelected = !it.isSelected) else it }
    }

    private object ReducerImpl : Reducer<ChooseZoneStore.State, Message> {
        override fun ChooseZoneStore.State.reduce(msg: Message): ChooseZoneStore.State =
            when (msg) {
                is Message.onZoneClick -> copy(zones = msg.zones)
            }

    }

}