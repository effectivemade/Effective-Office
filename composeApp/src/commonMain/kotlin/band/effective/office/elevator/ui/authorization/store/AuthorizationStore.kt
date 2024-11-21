package band.effective.office.elevator.ui.authorization.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.models.UserData
import com.arkivanov.mvikotlin.core.store.Store

interface AuthorizationStore :
    Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> {

    sealed interface Intent {
        data class ChangeEmail(val email: String) : Intent
        data class ChangePhoneNumber(val phoneNumber: String) : Intent
        data class ChangeName(val name: String) : Intent
        data class ChangePost(val post: String) : Intent
        data class ChangeTelegram(val telegram: String) : Intent

        data class UpdateUserInfo(val user: User) : Intent
    }

    data class State(
        val userData: User = User.defaultUser
    )

    sealed interface Label {
        object AuthorizationSuccess : Label
    }
}