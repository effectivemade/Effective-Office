package band.effective.office.elevator.ui.authorization.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    val getUserUseCase: GetUserUseCase by inject()
    fun create(): AuthorizationStore =
        object : AuthorizationStore,
            Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
                name = "Authorization store",
                initialState = AuthorizationStore.State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(Action.LoadUserData)
                    }
                }
            ) {
        }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationStore.Intent, Action, AuthorizationStore.State, Msg, AuthorizationStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) {
            when (intent) {
                is AuthorizationStore.Intent.ChangeEmail -> dispatch(Msg.ChangeEmail(intent.email))
                is AuthorizationStore.Intent.ChangeName -> dispatch(Msg.ChangeName(intent.name))
                is AuthorizationStore.Intent.ChangePhoneNumber -> dispatch(Msg.ChangePhoneNumber(intent.phoneNumber))
                is AuthorizationStore.Intent.ChangePost -> dispatch(Msg.ChangePost(intent.post))
                is AuthorizationStore.Intent.ChangeTelegram -> dispatch(Msg.ChangeTelegram(intent.telegram))
                is AuthorizationStore.Intent.UpdateUserInfo -> dispatch(Msg.ChangeUser(intent.user))
            }
        }

        override fun executeAction(action: Action, getState: () -> AuthorizationStore.State) {
            when(action) {
                is Action.LoadUserData -> {
                    featchUserInfo()
                }
            }
        }

        private fun featchUserInfo() {
            scope.launch(Dispatchers.IO) {
                getUserUseCase.execute().collect{ user ->
                    withContext(Dispatchers.Main) {
                        when(user) {
                            is Either.Error -> {
                                println("Error get user")
                            }
                            is Either.Success -> {
                                dispatch(Msg.ChangeUser(user.data))
                            }
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl: Reducer<AuthorizationStore.State, Msg > {
        override fun AuthorizationStore.State.reduce(msg: Msg): AuthorizationStore.State =
            when(msg) {
                is Msg.ChangeEmail -> copy(userData = userData.copy(email = msg.email))
                is Msg.ChangeName -> copy(userData = userData.copy(userName = msg.name))
                is Msg.ChangePhoneNumber -> copy(userData = userData.copy(phoneNumber = msg.phoneNumber))
                is Msg.ChangePost -> copy(userData = userData.copy(post = msg.post))
                is Msg.ChangeTelegram -> copy(userData = userData.copy(telegram = msg.telegram))
                is Msg.ChangeUser -> copy(userData = msg.user)
            }
    }

    private sealed interface Msg {
        data class ChangeEmail(val email: String) : Msg
        data class ChangePhoneNumber(val phoneNumber: String) : Msg
        data class ChangeName(val name: String) : Msg
        data class ChangePost(val post: String) : Msg
        data class ChangeTelegram(val telegram: String) : Msg

        data class ChangeUser(val user: User) : Msg
    }

    private sealed interface Action{
        data object LoadUserData : Action
    }
}