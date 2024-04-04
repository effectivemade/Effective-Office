package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.useCase.GetUserAvatarUseCase
import band.effective.office.network.dto.avatar.AvatarRequestBody

class AvatarInteractor(
    private val getUserAvatarUseCase: GetUserAvatarUseCase
) {
    suspend fun get(email: String, username: String, password: String) =
        getUserAvatarUseCase.get(avatarRequestBody = AvatarRequestBody(username, password, email))
}