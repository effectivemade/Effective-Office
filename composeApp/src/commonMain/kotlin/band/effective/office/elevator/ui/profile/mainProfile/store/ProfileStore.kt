package band.effective.office.elevator.ui.profile.mainProfile.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.network.dto.avatar.AvatarDTO
import com.arkivanov.mvikotlin.core.store.Store

interface ProfileStore : Store<ProfileStore.Intent, ProfileStore.State, ProfileStore.Label> {

    sealed interface Intent {
        object SignOutClicked : Intent
    }

    data class State(
        val isLoading: Boolean = true,
        val user: User,
        val userAvatar: AvatarDTO
    )

    sealed interface Label {
        object OnSignedOut : Label
    }
}