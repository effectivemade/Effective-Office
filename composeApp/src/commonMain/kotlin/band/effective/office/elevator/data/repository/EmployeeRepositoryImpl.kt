package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.toEmployeeInfo
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.elevator.utils.map
import band.effective.office.network.api.Api
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update


class EmployeeRepositoryImpl(
    private val api: Api
) : EmployeeRepository {

    private val employeeList: MutableStateFlow<Either<ErrorWithData<List<EmployeeInfo>>, List<EmployeeInfo>>> =
        MutableStateFlow(
            Either.Error(
                ErrorWithData<List<EmployeeInfo>>(
                    ErrorResponse(0, ""), null
                )
            )
        )

    override suspend fun getEmployeesInfo(): Flow<Either<ErrorWithData<List<EmployeeInfo>>, List<EmployeeInfo>>> =
        flow {
            val employees = api.getUsers(tag = "employee").convert(employeeList.value)
            employeeList.update { employees }
            emit(employees)
        }

    override suspend fun getEmployeeById(id: String): Flow<Either<ErrorWithData<EmployeeInfo>, EmployeeInfo>> =
        flow {
            val employee =
                api.getUser(id = id).convert(employeeId = id, allEmployees = employeeList.value)
            emit(employee)
        }

    private fun Either<ErrorResponse, List<UserDTO>>.convert(
        oldValue: Either<ErrorWithData<List<EmployeeInfo
                >>, List<EmployeeInfo>>
    ) =
        map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = when (oldValue) {
                    is Either.Error -> oldValue.error.saveData
                    is Either.Success -> oldValue.data
                }
            )
        },
            successMapper = { user ->
                user.map { it.toEmployeeInfo() }
            }
        )


    private fun Either<ErrorResponse, UserDTO>.convert(
        employeeId: String,
        allEmployees: Either<ErrorWithData<List<EmployeeInfo>>, List<EmployeeInfo>>
    ) =
        map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = when (allEmployees) {
                    is Either.Error -> allEmployees.error.saveData?.find { it.id == employeeId }
                    is Either.Success -> allEmployees.data.find { it.id == employeeId }
                }
            )
        },
            successMapper = { user ->
                user.toEmployeeInfo()
            })
}
