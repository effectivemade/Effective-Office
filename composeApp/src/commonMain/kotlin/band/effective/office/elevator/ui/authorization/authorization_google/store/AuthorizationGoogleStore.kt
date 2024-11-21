package band.effective.office.elevator.ui.authorization.authorization_google.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.models.UserData
import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationGoogleStore :
    Store<AuthorizationGoogleStore.Intent, AuthorizationGoogleStore.State, AuthorizationGoogleStore.Label> {

    sealed interface Intent {
        object SignInButtonClicked : Intent
    }

    class State

    sealed interface Label {
        data class AuthorizationSuccess(val user: User) : Label

        data class AuthorizationFailure(val message: String) : Label
    }
}