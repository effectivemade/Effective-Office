package band.effective.office.elevator.ui.main.components

import band.effective.office.elevator.ui.main.store.MainStore

data class PopupItem(
    val title: String,
    val intent: MainStore.Intent
)
