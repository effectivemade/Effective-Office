package band.effective.office.tv.utils

object DayCountFromString {
    private const val dayInMonth = 30

    fun getDayCount(str: String) = when {
        str.isDaysCount() -> str.getDaysCount()
        str.isMonthCount() -> str.getMonthCount() * dayInMonth
        else -> -1
    }

    private fun String.isDaysCount() = lowercase().contains("ден")
    private fun String.isMonthCount() = lowercase().contains("мес")
    private fun String.getDaysCount() = lowercase().substring(0, lowercase().indexOf("д")).toInt()
    private fun String.getMonthCount() = lowercase().substring(0, lowercase().indexOf("м")).toInt()
}