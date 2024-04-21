package office.effective.model

import office.effective.common.constants.BookingConstants
import java.text.SimpleDateFormat
import java.util.*


data class RecurrenceModel (
    val interval: Int? = null,
    val freq: String,
    val count: Int? = null,
    val until: Long? = null,
    val byDay: List<Int> = listOf(),
    val byMonth: List<Int> = listOf(),
    val byYearDay: List<Int> = listOf(),
    val byHour: List<Int> = listOf()
) {
    companion object {
        fun RecurrenceModel.toRecurrence(): Recurrence =
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
            val time = GregorianCalendar().apply {
                timeZone = TimeZone.getTimeZone("GMT")
                timeInMillis = millisDate
            }
            return SimpleDateFormat(BookingConstants.UNTIL_FORMAT).format(time.time)
        }
    }
}