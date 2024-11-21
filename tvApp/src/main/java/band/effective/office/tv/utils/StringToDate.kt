package band.effective.office.tv.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

fun String.getDate(): GregorianCalendar {
    val components = split(".")
    if (components.size < 3) return GregorianCalendar()
    return GregorianCalendar(
        components[2].toInt(),
        components[1].toInt() - 1,
        components[0].toInt()
    )
}

fun String.getDate(pattern: String): GregorianCalendar{
    val df = SimpleDateFormat(pattern)
    val date = df.parse(this)
    val cal = GregorianCalendar()
    if (date != null) {
        cal.time = date
        cal.set(Calendar.YEAR, GregorianCalendar().get(Calendar.YEAR))
    }
    val date2 = cal.time
    return cal
}