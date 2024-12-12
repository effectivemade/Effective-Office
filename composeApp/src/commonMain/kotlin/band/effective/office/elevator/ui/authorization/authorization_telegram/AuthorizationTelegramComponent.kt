package band.effective.office.elevator.ui.authorization.authorization_telegram

import band.effective.office.elevator.ui.authorization.authorization_telegram.store.AuthorizationTelegramStore
import band.effective.office.elevator.ui.authorization.authorization_telegram.store.AuthorizationTelegramStoreFactory
import band.effective.office.elevator.domain.validator.UserInfoValidator
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AuthorizationTelegramComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val validator: UserInfoValidator,
    private val telegramNick: String,
    private val output: (Output) -> Unit,
    private val changeTelegramNick: (String) -> Unit
) : ComponentContext by componentContext {

    private val authorizationTelegramStore =
        instanceKeeper.getStore {
            AuthorizationTelegramStoreFactory(
                storeFactory = storeFactory,
                validator = validator,
                nick = telegramNick
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val nick: StateFlow<AuthorizationTelegramStore.State> = authorizationTelegramStore.stateFlow

    val label: Flow<AuthorizationTelegramStore.Label> = authorizationTelegramStore.labels

    fun onEvent(event: AuthorizationTelegramStore.Intent) {
        authorizationTelegramStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    fun changeTG(telegramNick: String) = changeTelegramNick(telegramNick)

    sealed class Output {
        object OpenContentFlow : Output()

        object OpenProfileScreen : Output()
    }
}
