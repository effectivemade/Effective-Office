package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.repository.AvatarRepository
import band.effective.office.network.dto.avatar.AvatarDTO
import band.effective.office.network.dto.avatar.AvatarRequestBody
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow

class GetUserAvatarUseCase(private val avatarRepository: AvatarRepository) {
    suspend fun execute(avatarRequestBody: AvatarRequestBody): Flow<Either<ErrorWithData<AvatarDTO>, AvatarDTO>> {
        with(avatarRequestBody) {
            return avatarRepository.getAvatar(
                email, password, username
            )
        }
    }
}