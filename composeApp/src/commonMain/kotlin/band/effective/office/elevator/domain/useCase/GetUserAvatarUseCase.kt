package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.repository.AvatarRepository
import band.effective.office.network.dto.avatar.AvatarRequestBody

class GetUserAvatarUseCase(private val avatarRepository: AvatarRepository) {
    suspend fun get(avatarRequestBody: AvatarRequestBody) {
        with(avatarRequestBody) {
            avatarRepository.getAvatar(
                email, password, username
            )
        }
    }
}