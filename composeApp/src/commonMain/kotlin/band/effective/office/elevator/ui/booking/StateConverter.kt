package band.effective.office.elevator.ui.booking

import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookAccept.store.BookAcceptStore
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store.BookPeriodStore
import kotlinx.datetime.atTime

object StateConverter {
    fun BookingStore.State.toBookAcceptState(): BookAcceptStore.State = BookAcceptStore.State(
        seatName = selectedSeatName,
        dateOfStart = selectedStartDate.atTime(selectedStartTime),
        dateOfEnd = selectedFinishDate.atTime(selectedFinishTime),
        bookingPeriod = bookingPeriod,
        typeEndPeriodBooking = typeOfEnd,
        repeatBooking = repeatBooking,
        modalState = BookAcceptStore.ModalState.HIDDEN,
        bookingId = selectedWorkspaceId
    )

    fun BookingStore.State.toBookPeriodState(): BookPeriodStore.State =
        BookPeriodStore.State(
            bookingId = selectedWorkspaceId,
            startDate = selectedStartDate,
            startTime = selectedStartTime,
            finishDate = selectedFinishDate,
            finishTime = selectedFinishTime,
            repeatBooking = repeatBooking,
            switchChecked = false,
            dateOfEndPeriod = dateOfEndPeriod,
            bookingPeriod = bookingPeriod,
            endPeriodBookingType = typeOfEnd
        )
}