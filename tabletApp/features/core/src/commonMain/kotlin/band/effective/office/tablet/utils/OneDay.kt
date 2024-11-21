package band.effective.office.tablet.utils

import java.util.Calendar

fun Calendar.oneDay(second: Calendar): Boolean =
    equalField(this, second, Calendar.YEAR)
            && equalField(this, second, Calendar.MONTH)
            && equalField(this, second, Calendar.DAY_OF_MONTH)

private fun equalField(firstCalendar: Calendar, secondCalendar: Calendar, field: Int) =
    firstCalendar.get(field) == secondCalendar.get(field)