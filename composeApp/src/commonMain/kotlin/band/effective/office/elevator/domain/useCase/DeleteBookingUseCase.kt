package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.ui.models.ReservedSeat
import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalTime

class DeleteBookingUseCase(
    private val repository: BookingRepository
)  {
    suspend fun deleteBooking(seat: ReservedSeat, coroutineScope: CoroutineScope) = repository.deleteBooking(
        BookingInfo(
            id = seat.bookingId,
            ownerId = seat.ownerId,
            workSpaceId = "",
            seatName = seat.seatName,
            dateOfStart = LocalDateTime(date = seat.bookingDate, time = seat.bookingTime.substringBefore(" -").toLocalTime()),
            dateOfEnd = LocalDateTime(date = seat.bookingDate, time = seat.bookingTime.substringAfter("- ").toLocalTime())
        )
    )
}