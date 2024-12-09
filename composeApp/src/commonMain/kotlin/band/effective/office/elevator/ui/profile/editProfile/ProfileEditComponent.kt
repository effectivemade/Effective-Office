package band.effective.office.elevator.ui.profile.editProfile

import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileEditComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    private val profileEditStore = instanceKeeper.getStore {
        ProfileEditStoreFactory(
            storeFactory = storeFactory,
        ).create()
    }

    val state: StateFlow<ProfileEditStore.State> = profileEditStore.stateFlow

    val label: Flow<ProfileEditStore.Label> = profileEditStore.labels

    fun onEvent(event: ProfileEditStore.Intent) {
        profileEditStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed interface Output {
        data object NavigationBack : Output
    }
}
