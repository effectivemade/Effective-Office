package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun getEmployeesInfo():
            Flow<Either<ErrorWithData<List<EmployeeInfo>>, List<EmployeeInfo>>>

    suspend fun getEmployeeById(id: String):
            Flow<Either<ErrorWithData<EmployeeInfo>, EmployeeInfo>>

}