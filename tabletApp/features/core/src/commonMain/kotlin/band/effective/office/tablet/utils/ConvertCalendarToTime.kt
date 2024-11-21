package band.effective.office.tablet.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.date() = "${this.get(Calendar.DAY_OF_MONTH)} " +
        SimpleDateFormat("MMMM", Locale("ru")).format(this.time)

fun Calendar.time24() = SimpleDateFormat("HH:mm", Locale("ru")).format(this.time)
