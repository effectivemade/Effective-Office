package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.data.ApiResponse

interface OfficeElevatorRepository {
    suspend fun call(): ApiResponse<Unit, Error>
}
