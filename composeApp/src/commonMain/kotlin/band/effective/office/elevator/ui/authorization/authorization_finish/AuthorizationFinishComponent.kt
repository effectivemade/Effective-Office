package band.effective.office.elevator.ui.authorization.authorization_finish

import band.effective.office.elevator.ui.authorization.authorization_finish.store.AuthorizationFinishStore
import band.effective.office.elevator.ui.authorization.authorization_finish.store.AuthorizationFinishStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class AuthorizationFinishComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val name: String,
    private val post: String,
    private val avatarUrl: String,
    private val output: (Output) -> Unit,
) : ComponentContext by componentContext {

    private val authorizationProfileStore =
        instanceKeeper.getStore {
            AuthorizationFinishStoreFactory(
                storeFactory = storeFactory,
                name = name,
                post = post,
                avatarUrl = avatarUrl,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val user: StateFlow<AuthorizationFinishStore.State> = authorizationProfileStore.stateFlow

    fun onOutput(output: Output) {
        output(output)
    }

    sealed interface Output {
        data object OpenNoBookingScreen: Output
    }
}
