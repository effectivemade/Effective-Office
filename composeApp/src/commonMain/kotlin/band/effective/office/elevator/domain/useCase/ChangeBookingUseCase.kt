package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.repository.BookingRepository

class ChangeBookingUseCase(
    private val repository: BookingRepository
) {
    suspend fun execute(bookingInfo: BookingInfo) {
        repository.changeBooking(bookingInfo = bookingInfo)
    }
}