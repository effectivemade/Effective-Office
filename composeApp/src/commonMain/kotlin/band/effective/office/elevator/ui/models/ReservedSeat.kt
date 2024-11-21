package band.effective.office.elevator.ui.models

import kotlinx.datetime.LocalDate

data class ReservedSeat(
    val bookingId: String,
    val ownerId: String,
    val seatName: String,
    val bookingDay: String,
    val bookingTime: String,
    val bookingDate: LocalDate
){
    companion object {
        val defaultSeat =
            ReservedSeat(
                bookingId = "",
                ownerId = "",
                seatName = "",
                bookingDay = "",
                bookingTime = "",
                bookingDate = LocalDate(2023,8,16)
            )
    }
}