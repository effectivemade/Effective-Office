package band.effective.office.tablet.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

object CalendarStringConverter {
    fun calendarToString(calendar: Calendar,pattern: String): String = SimpleDateFormat(pattern).format(calendar.time)
    fun stringToCalendar(str: String, pattern: String): Calendar {
        val calendar = GregorianCalendar()
        calendar.time = SimpleDateFormat(pattern).parse(str)
        return calendar
    }
}