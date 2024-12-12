package band.effective.office.elevator.ui.authorization.authorization_phone.store

import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore.*
import band.effective.office.elevator.domain.validator.UserInfoValidator
import com.arkivanov.mvikotlin.core.store.Bootstrapper
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

internal class AuthorizationPhoneStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: UserInfoValidator,
    private val userPhoneNumber: String
) : KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationPhoneStore =
        object : AuthorizationPhoneStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "Authorization phone",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Action.PushToken),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    private sealed interface Action {
        object PushToken : Action
    }

    sealed interface Msg {
        data class Error(
            val error: Boolean
        ) : Msg

        data class Data(
            val phoneNumber: String,
        ) : Msg
    }

    private object ReducerImpl : Reducer<AuthorizationPhoneStore.State, Msg> {
        override fun State.reduce(msg: Msg): AuthorizationPhoneStore.State =
            when (msg) {
                is Msg.Data -> copy(
                    phoneNumber = msg.phoneNumber,
                )

                is Msg.Error -> copy(
                    isErrorPhoneNumber = msg.error
                )
            }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.BackButtonClicked -> back()
                Intent.ContinueButtonClicked -> checkPhoneNumber(getState().phoneNumber)
                is Intent.PhoneNumberChanged ->
                    dispatch(
                        Msg.Data(
                            phoneNumber = intent.phoneNumber
                        )
                    )

            }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.PushToken -> {
                    initPhoneNumber()
                }
            }
        }

        private fun checkPhoneNumber(phoneNumber: String) {
            if (validator.checkPhone(phoneNumber)) {
                publish(Label.AuthorizationPhoneSuccess)
                dispatch(
                    Msg.Error(
                        error = false
                    )
                )
            } else {
                dispatch(
                    Msg.Error(
                        error = true
                    )
                )
                publish(Label.AuthorizationPhoneFailure)
            }
        }

        private fun back() {
            publish(AuthorizationPhoneStore.Label.ReturnInGoogleAuthorization)
        }

        private fun initPhoneNumber() {
            val phoneNumber = if (userPhoneNumber.length > UserInfoValidator.phoneNumberSize)
                userPhoneNumber.substring(
                    startIndex = userPhoneNumber.length % UserInfoValidator.phoneNumberSize,
                )
            else
                userPhoneNumber

            dispatch(AuthorizationPhoneStoreFactory.Msg.Data(phoneNumber = phoneNumber))

        }
    }
}
