package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend fun execute(creatingBookModel: CreatingBookModel): Flow<Either<ErrorResponse, BookingInfo>> {
        // TODO (Artem Gruzdev) Then backend fix unlimited book, delete this
        val changedTypeEnd = when (val typeEnd = creatingBookModel.typeOfEndPeriod) {
            TypeEndPeriodBooking.Never -> TypeEndPeriodBooking.CountRepeat(100)
            else -> typeEnd
        }
        val changedBookModel = creatingBookModel.copy(typeOfEndPeriod = changedTypeEnd)
        return repository.createBook(creatingBookModel = changedBookModel)
    }
}