package band.effective.office.tablet.utils

import java.util.Calendar

fun Calendar.removeSeconds(): Calendar = this
    .apply {
        set(Calendar.MILLISECOND, 0)
        set(Calendar.SECOND, 0)
}