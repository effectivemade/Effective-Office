package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.repository.EmployeeRepository

class EmployeeUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke() = repository.getEmployeesInfo()

}