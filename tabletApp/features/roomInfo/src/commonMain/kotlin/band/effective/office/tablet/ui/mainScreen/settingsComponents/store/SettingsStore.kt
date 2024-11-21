package band.effective.office.tablet.ui.mainScreen.settingsComponents.store

import band.effective.office.tablet.domain.model.RoomsEnum
import com.arkivanov.mvikotlin.core.store.Store

interface SettingsStore : Store<SettingsStore.Intent, SettingsStore.State, Nothing> {
    sealed interface Intent {
        object OnExitApp : Intent
        data class ChangeCurrentNameRoom(val nameRoom: String) : Intent
        object SaveData : Intent
    }

    data class State(
        val rooms: List<String>,
        val currentName: String,
        val loading: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    rooms = RoomsEnum.entries.map { room -> room.nameRoom }.toList(),
                    currentName = "",
                    loading = true
                )
        }
    }
}