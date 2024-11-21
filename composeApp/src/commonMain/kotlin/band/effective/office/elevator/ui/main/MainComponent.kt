package band.effective.office.elevator.ui.main

import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.main.store.MainStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) :
    ComponentContext by componentContext {

    private val mainStore =
        instanceKeeper.getStore {
            MainStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MainStore.State> = mainStore.stateFlow

    val label: Flow<MainStore.Label> = mainStore.labels

    fun onEvent(event: MainStore.Intent) {
        mainStore.accept(event)
    }

    fun onOutput(output: Output) {
       output(output)
    }

    sealed interface Output {

        object OpenMap : Output

        data class DeleteBooking(val id: String) : Output

        object OpenBookingScreen : Output

    }
}
