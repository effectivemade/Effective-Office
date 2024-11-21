package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.toUIModel
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.network.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class AboutEmployeeInteractor(
    private val employeeRepository: EmployeeRepository,
    private val bookingRepository: BookingRepository
) {

    suspend fun getBookingsForUser(
        ownerId:String,
        bookingsFilter: BookingsFilter,
        beginDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        bookingRepository
            .getBookingsForUser(
                ownerId = ownerId,
                endDate = endDateTime,
                beginDate = beginDateTime,
                bookingsFilter = bookingsFilter
            )
            .map()


    private fun Flow<Either<ErrorWithData<List<BookingInfo>>, List<BookingInfo>>>.map() =
        this.map{ response ->
            when(response) {
                is Either.Error -> Either.Error(ErrorWithData(
                    error = response.error.error,
                    saveData = response.error.saveData?.toUIModel()
                ))
                is Either.Success -> Either.Success(response.data.toUIModel())
            }
        }
}