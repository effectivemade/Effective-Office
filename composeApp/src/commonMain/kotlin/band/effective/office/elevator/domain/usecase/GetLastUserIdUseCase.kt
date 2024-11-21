package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.repository.UserRepository

class GetLastUserIdUseCase (private val userRepository: UserRepository){
    suspend fun execute():String{
       return userRepository.getLastUserId()
    }
}