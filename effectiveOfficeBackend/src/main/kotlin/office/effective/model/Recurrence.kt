package office.effective.model

import model.RecurrenceDTO
import office.effective.common.constants.BookingConstants
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*

enum class Freq {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

sealed class Ending {
    data class Count(val value: Int) : Ending()
    data class Until(val value: String) : Ending()
    object Empty : Ending()
}

data class Recurrence(
    val interval: Int,
    val freq: Freq,
    val ending: Ending,
    val byDay: List<Int>,
    val byMonth: List<Int>,
    val byYearDay: List<Int>,
    val byHour: List<Int>
) {
    companion object {
        fun RecurrenceDTO.toRecurrence(): Recurrence =
            Recurrence(interval = (interval ?: 0).apply { if (this < 0) throw IllegalArgumentException("Interval must be greater then 0") },
                freq = Freq.valueOf(freq),
                ending = when {
                    count != null && until != null -> throw IllegalArgumentException()
                    count != null -> Ending.Count(count)
                    until != null -> Ending.Until(toDateRfc5545(until))

                    else -> Ending.Empty
                },
                byDay = byDay.onEach { day -> if (day !in 1..7) throw IllegalArgumentException() },
                byMonth = byMonth.onEach { month -> if (month !in 1..12) throw IllegalArgumentException() },
                byYearDay = byYearDay.onEach { yearDay -> if (yearDay !in 1..365) throw IllegalArgumentException() },
                byHour = byHour.onEach { hour -> if (hour !in 0..23) throw IllegalArgumentException() })

        /**
         * Converts milliseconds date into exdate format from RFC5545
         *
         * @param millisDate - date in milliseconds ([Long])
         * @return [String] - date in DATE-TIME (RFC5545). Example: [BookingConstants.UNTIL_FORMAT]
         * */
        private fun toDateRfc5545(millisDate: Long): String {
            val time = GregorianCalendar().apply { timeInMillis = millisDate + 86400000 }
            return SimpleDateFormat(BookingConstants.UNTIL_FORMAT).format(time.time)
        }
    }

    public fun toDto(): RecurrenceDTO = RecurrenceDTO(
        interval = if (interval != 0) interval else null,
        freq = freq.name,
        count = if (ending is Ending.Count) ending.value else null,
        until = if (ending is Ending.Until) parseUntil(ending.value) else null,
        byDay = byDay,
        byMonth = byMonth,
        byYearDay = byYearDay,
        byHour = byHour
    )

    private fun parseUntil(untilStr: String): Long {
        val date: Date = SimpleDateFormat(BookingConstants.UNTIL_FORMAT).parse(untilStr)
        println("UNTIL: $untilStr")
        return date.time
    }
}