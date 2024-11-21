package band.effective.office.elevator.ui.bottomSheets.bookingSheet.chooseZoneSheet.store

import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import com.arkivanov.mvikotlin.core.store.Store

interface ChooseZoneStore : Store<ChooseZoneStore.Intent, ChooseZoneStore.State, Nothing> {
    sealed interface Intent {
        object onCloseRequest : Intent
        object onConfirmRequest : Intent
        data class onZoneClick(val zone: WorkspaceZoneUI) : Intent
    }

    data class State(
        val zones: List<WorkspaceZoneUI>
    ) {
        companion object {
            val default = State(zones = listOf())
        }
    }
}