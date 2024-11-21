package band.effective.office.tv.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

fun calendarToString(calendar: Calendar = GregorianCalendar(), pattern: String = "dd MMMM, HH:mm"):String =
    SimpleDateFormat(pattern).format(calendar.time)