package band.effective.office.tablet.ui.mainScreen.slotComponent.store

import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.ui.mainScreen.slotComponent.model.SlotUi
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar

interface SlotStore : Store<SlotStore.Intent, SlotStore.State, Nothing> {
    sealed interface Intent {
        data class ClickOnSlot(val slot: SlotUi) : Intent
        data class UpdateRequest(val room: String, val refresh: Boolean = true) : Intent
        data class UpdateDate(val newDate: Calendar) : Intent
        data class Delete(val slot: Slot, val onDelete: () -> Unit) : Intent
        data class Loading(val slot: Slot) : Intent
        data class OnCancelDelete(val slot: SlotUi.DeleteSlot) : Intent
    }

    data class State(
        val slots: List<SlotUi>
    ) {
        companion object {
            val initValue =
                State(slots = listOf())
        }

    }
}