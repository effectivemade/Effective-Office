package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.ProfileRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.map

class GetUserUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute() = profileRepository.getUser()
    suspend fun executeInFormat() =
        profileRepository.getUser().map { user ->
            when (user) {
                is Either.Success -> Either.Success(user.data.formatToUI())
                is Either.Error -> Either.Error(
                    ErrorWithData(
                        error = user.error.error,
                        saveData = user.error.saveData?.formatToUI()
                    )
                )
            }
        }

    private fun User.formatToUI() =
        User(
            userName = userName,
            id = id,
            imageUrl = imageUrl,
            post = post,
            phoneNumber = phoneNumber,
            telegram = telegram,
            email = email
        )
}