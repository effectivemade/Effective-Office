package band.effective.office.elevator.ui.booking.models

import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.DayOfWeek
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.utils.getFormattedDate
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

//TODO (Artem Gruzdev) refactor this code :(
class Frequency(
    private val days: List<Pair<String, Int>>,
    private val researchEnd: Triple<Pair<String, String>, String, String> // WTF???
) {

    fun getDays(): List<DayOfWeek> {
        val list = listOf(
            DayOfWeek.Monday,
            DayOfWeek.Tuesday,
            DayOfWeek.Wednesday,
            DayOfWeek.Thursday,
            DayOfWeek.Friday,
            DayOfWeek.Saturday
        )

        return days.map { list[it.second] }
    }

    fun getResearchEnd(): Triple<Pair<String, String>, String, String> {

        return researchEnd
    }

    fun getBookPeriod(): BookingPeriod =
        when (researchEnd.third) {
            "Week" -> {
                BookingPeriod.Week(
                    weekPeriod = researchEnd.second.toInt(),
                    selectedDayOfWeek = getDays()
                )
            }
            "Month" -> {
                BookingPeriod.Month(monthPeriod = researchEnd.second.toInt())
            }
            "Year" -> {
                BookingPeriod.Year(yearPeriod = researchEnd.second.toInt())
            }
            else -> BookingPeriod.NoPeriod
        }

    fun getTypeOfEndBooking() =
        when(researchEnd.first.first) {
            "Date" -> {
                val date = researchEnd.first.second
                TypeEndPeriodBooking.DatePeriodEnd(
                    date = LocalDate.parse(date)
                )
            }
            "CoupleTimes" -> TypeEndPeriodBooking.CountRepeat(
                count = researchEnd.first.second.toInt()
            )
            "Never" -> TypeEndPeriodBooking.Never
            else ->TypeEndPeriodBooking.Never
        }

    override fun toString(): String {
        val distinctWeekdays = days.distinct()
        return distinctWeekdays.joinToString(", ") { it.first }
    }
}