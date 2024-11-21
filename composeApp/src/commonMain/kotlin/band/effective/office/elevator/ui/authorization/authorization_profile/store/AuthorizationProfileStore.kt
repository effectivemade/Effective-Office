package band.effective.office.elevator.ui.authorization.authorization_profile.store

import band.effective.office.elevator.domain.models.UserData
import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationProfileStore :
    Store<AuthorizationProfileStore.Intent, AuthorizationProfileStore.State, AuthorizationProfileStore.Label> {

    sealed interface Intent {
        data class NameChanged(val name: String) : Intent
        data class PostChanged(val post: String) : Intent
        object ContinueButtonClicked : Intent
        object BackButtonClicked : Intent
    }

    data class State(
        var name: String = "",
        var post: String = "",
        var isLoading: Boolean = false,
        var isError: Boolean = false,
        var isErrorName: Boolean = false,
        var isErrorPost: Boolean = false
    )

    sealed interface Label {
        object AuthorizationProfileSuccess : Label

        object AuthorizationProfileFailure : Label

        object ReturnInPhoneAuthorization : Label
    }
}