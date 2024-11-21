package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.repository.OfficeElevatorRepository

class ElevatorCallUseCase(
     private val repository: OfficeElevatorRepository
) {
    suspend fun callElevator() = repository.call()
}