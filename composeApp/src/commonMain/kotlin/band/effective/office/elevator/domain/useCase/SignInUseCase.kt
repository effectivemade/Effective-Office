package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.repository.AuthorizationRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SignInUseCase(
    private val signInClient: GoogleSignIn,
    private val authorizationRepository: AuthorizationRepository
) {
    suspend fun signIn(): Flow<Either<*, *>> = flow {
        when (val user = signInClient.retrieveAuthorizedUser()) {
            is ApiResponse.Error.HttpError -> TODO()
            ApiResponse.Error.NetworkError -> TODO()
            ApiResponse.Error.SerializationError -> TODO()
            ApiResponse.Error.UnknownError -> {
                emit(Either.Error(Unit))
            }
            is ApiResponse.Success -> {
                authorizationRepository
                    .authorizeUser(idToken = user.body.idToken!!, email = user.body.email, imageUrl = user.body.photoUrl)
                    .collect { response ->
                        emit(
                            when (response) {
                                is Either.Error -> Either.Error(Unit)
                                is Either.Success -> Either.Success(Unit)
                            }
                        )
                    }
            }
        }
    }
}