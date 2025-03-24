package band.effective.office.elevator.ui.authorization.authorization_google

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class)
class AuthorizationGoogleComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val authorizationStore =
        instanceKeeper.getStore {
            AuthorizationGoogleStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val state: StateFlow<AuthorizationGoogleStore.State> = authorizationStore.stateFlow

    val label: Flow<AuthorizationGoogleStore.Label> = authorizationStore.labels


    fun onEvent(event: AuthorizationGoogleStore.Intent) {
        authorizationStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        data class OpenAuthorizationPhoneScreen(val userData: User) : Output()
    }

}
