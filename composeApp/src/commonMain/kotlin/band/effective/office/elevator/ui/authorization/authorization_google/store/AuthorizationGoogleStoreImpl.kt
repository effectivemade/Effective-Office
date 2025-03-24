package band.effective.office.elevator.ui.authorization.authorization_google.store

import band.effective.office.elevator.domain.useCase.AuthorizationUseCase
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.Intent
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.Label
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthorizationGoogleStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val authorizationUseCase: AuthorizationUseCase by inject()

    fun create(): AuthorizationGoogleStore =
        object : AuthorizationGoogleStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AuthorizationStore",
            initialState = State(isAuthInProgress = false),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private sealed interface Msg {
        data class UpdateInProgress(val isInProgress: Boolean) : Msg
    }

    private object ReducerImpl : Reducer<State, Msg> {

        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.UpdateInProgress -> copy(isAuthInProgress = msg.isInProgress)
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Unit, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.SignInButtonClicked -> if (!getState().isAuthInProgress) {
                    startAuthorization()
                }
            }
        }

        private fun startAuthorization() {
            dispatch(Msg.UpdateInProgress(true))
            scope.launch {
                authorizationUseCase.authorize(
                    scope = scope,
                    successCallBack = {
                        publish(Label.AuthorizationSuccess(it))
                        dispatch(Msg.UpdateInProgress(false))
                    },
                    failureCallBack = {
                        publish(Label.AuthorizationFailure(it))
                        dispatch(Msg.UpdateInProgress(false))
                    }
                )
            }
        }
    }
}
