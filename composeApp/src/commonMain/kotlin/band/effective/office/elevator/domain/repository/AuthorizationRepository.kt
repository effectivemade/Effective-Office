package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.User
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {
    suspend fun authorizeUser(idToken: String, email: String, imageUrl: String?): Flow<Either<ErrorResponse, User>>

     fun logout()
}