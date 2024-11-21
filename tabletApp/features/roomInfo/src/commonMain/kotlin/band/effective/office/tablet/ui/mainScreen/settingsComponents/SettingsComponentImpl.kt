package band.effective.office.tablet.ui.mainScreen.settingsComponents

import band.effective.office.tablet.ui.mainScreen.settingsComponents.store.SettingsStore
import band.effective.office.tablet.ui.mainScreen.settingsComponents.store.SettingsStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.component.KoinComponent

class SettingsComponentImpl(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    val onExitApp: () -> Unit,
    val onMainScreen: () -> Unit
): ComponentContext by componentContext, SettingsComponent, KoinComponent {
    private val settingsStore = instanceKeeper.getStore {
        SettingsStoreFactory(storeFactory).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state = settingsStore.stateFlow

    override fun onIntent(intent: SettingsStore.Intent){
        when(intent) {
            is SettingsStore.Intent.ChangeCurrentNameRoom -> {
                settingsStore.accept(intent)
            }
            is SettingsStore.Intent.OnExitApp -> {
                onExitApp()
            }
            is SettingsStore.Intent.SaveData -> {
                settingsStore.accept(intent)
                onMainScreen()
            }
        }
    }
}