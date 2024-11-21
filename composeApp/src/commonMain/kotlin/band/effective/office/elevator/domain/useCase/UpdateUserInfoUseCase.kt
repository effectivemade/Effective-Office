package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.ProfileRepository

class UpdateUserInfoUseCase(private val userProfileRepository: ProfileRepository) {
    suspend fun execute(user: User) = userProfileRepository.updateUser(user)
}