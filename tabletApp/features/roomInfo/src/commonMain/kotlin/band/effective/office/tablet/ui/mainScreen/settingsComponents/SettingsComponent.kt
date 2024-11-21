package band.effective.office.tablet.ui.mainScreen.settingsComponents

import band.effective.office.tablet.ui.mainScreen.settingsComponents.store.SettingsStore
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {
    val state: StateFlow<SettingsStore.State>
    fun onIntent(intent: SettingsStore.Intent)
}