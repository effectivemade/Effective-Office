package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.User
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUser(): Flow<Either<ErrorWithData<User>, User>>
    suspend fun updateUser(user: User): Flow<Either<ErrorWithData<User>, User>>
}