package band.effective.office.elevator.data.repository

import band.effective.office.elevator.data.database.DBSource
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.models.toUser
import band.effective.office.elevator.domain.repository.AuthorizationRepository
import band.effective.office.elevator.utils.map
import band.effective.office.network.api.Api
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.KtorEtherClient
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthorizationRepositoryImpl(
    private val api: Api,
    private val bdSource: DBSource
) : AuthorizationRepository {

    override suspend fun authorizeUser(
        idToken: String,
        email: String,
        imageUrl: String?,
    ): Flow<Either<ErrorResponse, User>> = flow {
        KtorEtherClient.token.add(idToken)
        Napier.d {
            "Token: ${KtorEtherClient.token}"
        }
        val apiResponse = api.getUserByEmail(email = email)
        val userResponse = apiResponse.map(
            errorMapper = { it },
            successMapper = {
                val user = it.toUser()
                if (imageUrl != null) user.copy(imageUrl = imageUrl)
                else user
            }
        )
        when(userResponse) {
            is Either.Success -> {
                bdSource.update(user = userResponse.data, idToken = idToken)
            }
            else -> {}
        }
        emit(userResponse)
    }

    override fun logout() {
        bdSource.deleteUserData()
    }

    private suspend fun getUserInfoFromDB() = bdSource.getCurrentUserInfo()
    private suspend fun Either<ErrorResponse, UserDTO>.convert(

    ): Either<ErrorWithData<User>, User> {
        val userFromDb = getUserInfoFromDB()

        return map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = userFromDb
            )
        },
            successMapper = { userDTO ->
                userDTO.toUser()
            })
    }

    private fun Either<ErrorWithData<User>, User>.getData() =
        when (this) {
            is Either.Error -> error.saveData
            is Either.Success -> data
        }

    private fun User?.packageEither(apiResponse: Either<ErrorWithData<User>, User>) =
        when (apiResponse) {
            is Either.Success -> Either.Success(this)
            is Either.Error -> Either.Error(
                ErrorWithData(
                    error = apiResponse.error.error,
                    saveData = this
                )
            )
        }
}