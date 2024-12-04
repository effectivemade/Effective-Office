package band.effective.office.elevator.ui.profile.editProfile.store

import band.effective.office.elevator.domain.models.User
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource

interface ProfileEditStore :
    Store<ProfileEditStore.Intent, ProfileEditStore.State, ProfileEditStore.Label> {
    sealed interface Intent {

        data object BackInProfileClicked : Intent

        data class SaveChangesClicked(
            val userName: String,
            val telegram: String,
            val post: String,
            val phoneNumber: String
        ) : Intent
    }

    sealed interface State {

        data object Loading : State

        data class Data(
            val user: User,
            val phoneNumberError: StringResource? = null,
            val nameError: StringResource? = null,
            val postError: StringResource? = null,
            val telegramError: StringResource? = null,
        ) : State
    }

    sealed interface Label {

        data object ReturnedInProfile : Label
        data object SavedChange : Label
        data object ServerError : Label
    }
}
