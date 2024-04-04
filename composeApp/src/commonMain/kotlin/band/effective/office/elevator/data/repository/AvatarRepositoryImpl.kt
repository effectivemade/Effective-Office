package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.repository.AvatarRepository
import band.effective.office.network.api.Api
import band.effective.office.network.dto.avatar.AvatarDTO
import band.effective.office.network.dto.avatar.AvatarRequestBody
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AvatarRepositoryImpl(
    private val api: Api
) : AvatarRepository {

    override suspend fun getAvatar(
        email: String,
        password: String,
        username: String
    ): Flow<Either<ErrorWithData<AvatarDTO>, AvatarDTO>> = flow {
        emit(getA())
    }

    private suspend fun getA(): Either<ErrorWithData<AvatarDTO>, AvatarDTO> {
        val avatarRequestBody = AvatarRequestBody(email = "sl1vka.run@gmail.com", password = "ye2gwEgs5snRcm6", username = "sl1vka") //TODO(Slava Deych: REMOVE HARDCODE LATER
        val either: Either<ErrorResponse, AvatarDTO> = api.getUserAvatar(avatarRequestBody = avatarRequestBody)
        return when (either) {
            is Either.Error -> {
                val errorEither = Either.Error(error = ErrorWithData(error = either.error, saveData = AvatarDTO()))
                return errorEither
            }
            is Either.Success -> {
                val successEither = Either.Success(data = either.data)
                return successEither
            }
        }
    }
}