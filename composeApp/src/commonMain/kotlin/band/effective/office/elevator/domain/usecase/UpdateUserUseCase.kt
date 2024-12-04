package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.ProfileRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.map

class UpdateUserUseCase (private val profileRepository: ProfileRepository) {
    suspend fun execute(user: User) =
        profileRepository.updateUser(User(
            id = user.id,
            imageUrl = user.imageUrl,
            userName = user.userName,
            post = user.post,
            phoneNumber = user.phoneNumber,
            telegram = user.telegram,
            email = user.email,
        )
        ).map{ user ->
            when (user) {
                is Either.Success -> Either.Success(user.data)
                is Either.Error -> Either.Error(
                        error = user.error.error,
                )
            }
        }

}
