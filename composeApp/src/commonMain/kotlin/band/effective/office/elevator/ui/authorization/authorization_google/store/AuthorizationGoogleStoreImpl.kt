package band.effective.office.elevator.ui.authorization.authorization_google.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.SignInResultCallback
import band.effective.office.elevator.domain.useCase.AuthorizationUseCase
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.Intent
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.Label
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.State
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStoreFactory
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthorizationGoogleStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject()
    private val authorizationUseCase: AuthorizationUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationGoogleStore =
        object : AuthorizationGoogleStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AuthorizationStore",
            initialState = State(),
            executorFactory = ::ExecutorImpl,
        ) {}

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, AuthorizationPhoneStoreFactory.Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.SignInButtonClicked -> startAuthorization()
            }
        }

        private fun startAuthorization() {
            scope.launch {
                authorizationUseCase.authorize(
                    scope = scope,
                    successCallBack = { publish(Label.AuthorizationSuccess(it)) },
                    failureCallBack = { publish(Label.AuthorizationFailure(it)) }
                )
            }
        }
    }
}
