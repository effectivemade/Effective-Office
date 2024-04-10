package band.effective.office.elevator.ui.booking.models.sheetData

import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store.BookPeriodStore
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class SelectedBookingPeriodState(
    val startDate: LocalDate,
    val startTime: LocalTime,
    val finishDate: LocalDate,
    val finishTime: LocalTime,
    val dateOfEndPeriod: LocalDate,
    val bookingPeriod: BookingPeriod,
    val endPeriodBookingType: TypeEndPeriodBooking,
    val repeatBooking: StringResource
)

fun BookPeriodStore.State.toSelectedBookingPeriod() =
    SelectedBookingPeriodState(
        startDate = startDate,
        startTime = startTime,
        finishDate = finishDate,
        finishTime = finishTime,
        dateOfEndPeriod = dateOfEndPeriod,
        bookingPeriod = bookingPeriod,
        endPeriodBookingType = endPeriodBookingType,
        repeatBooking = repeatBooking
    )
