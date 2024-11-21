package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.repository.ProfileRepository
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetUserByIdUseCase(private val employeeRepository: EmployeeRepository) {
    suspend fun execute(id:String) = employeeRepository.getEmployeeById(id)

    suspend fun executeInFormat(id: String) =
        employeeRepository.getEmployeeById(id).map { user ->
            when (user) {
                is Either.Success -> Either.Success(user.data.formatToUI())
                is Either.Error -> Either.Error(ErrorWithData(
                    error = user.error.error,
                    saveData = user.error.saveData?.formatToUI()
                ))
            }
        }

    private fun EmployeeInfo.formatToUI() =
        EmployeeInfo (
            id  = id,
            name = name,
            post = post,
            phoneNum = phoneNum.substring(2),
            telegramProfile = telegramProfile.substring(1),
            eMail = eMail,
            state = state,
            logoUrl = logoUrl
        )
}