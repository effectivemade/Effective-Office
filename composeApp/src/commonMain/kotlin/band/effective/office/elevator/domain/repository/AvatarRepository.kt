package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.network.dto.avatar.AvatarDTO
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow

interface AvatarRepository {
    suspend fun getAvatar(email: String, password: String, username: String) : Flow<Either<ErrorWithData<AvatarDTO>, AvatarDTO>>
}