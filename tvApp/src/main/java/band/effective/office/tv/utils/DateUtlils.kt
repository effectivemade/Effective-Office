package band.effective.office.tv.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar

object DateUtlils {
    fun getYearsFromStartDate(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.YEAR) - dateInfo[0].toInt()
    }
    fun getMonthsFromStartDate(date: String): Int {
        val dateInfo = date.split('-')
        return Calendar.getInstance()
            .get(Calendar.MONTH) + 1 - dateInfo[1].toInt()
    }

    fun getDatesByCurrentQuarter(): Pair<String, String> {
        val currentDate = GregorianCalendar()

        val startMonth: Int
        val endMonth: Int

        when (currentDate.get(Calendar.MONTH) + 1) {
            in 1..3 -> {
                startMonth = Calendar.JANUARY
                endMonth = Calendar.MARCH
            }
            in 4..6 -> {
                startMonth = Calendar.APRIL
                endMonth = Calendar.JUNE
            }
            in 7..9 -> {
                startMonth = Calendar.JULY
                endMonth = Calendar.SEPTEMBER
            }
            else -> {
                startMonth = Calendar.OCTOBER
                endMonth = Calendar.DECEMBER
            }
        }

        val startDate = GregorianCalendar(currentDate.get(Calendar.YEAR), startMonth, 1)

        val endDate = GregorianCalendar(
            currentDate.get(Calendar.YEAR),
            endMonth,
            1
        ).setLastDayOfMonth().fullDay()

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

        return Pair(dateFormatter.format(startDate.time), dateFormatter.format(endDate.time))
    }

}