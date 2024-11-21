package band.effective.office.elevator.utils

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

object MonthLocalizations {
    private val monthNames: Map<Month, Map<Locale, String>> = mapOf(
        Month.JANUARY to mapOf(Locale("ru") to "Январь", Locale("en") to "January"),
        Month.FEBRUARY to mapOf(Locale("ru") to "Февраль", Locale("en") to "February"),
        Month.MARCH to mapOf(Locale("ru") to "Март", Locale("en") to "March"),
        Month.APRIL to mapOf(Locale("ru") to "Апрель", Locale("en") to "April"),
        Month.MAY to mapOf(Locale("ru") to "Май", Locale("en") to "May"),
        Month.JUNE to mapOf(Locale("ru") to "Июнь", Locale("en") to "June"),
        Month.JULY to mapOf(Locale("ru") to "Июль", Locale("en") to "July"),
        Month.AUGUST to mapOf(Locale("ru") to "Август", Locale("en") to "August"),
        Month.SEPTEMBER to mapOf(Locale("ru") to "Сентябрь", Locale("en") to "September"),
        Month.OCTOBER to mapOf(Locale("ru") to "Октябрь", Locale("en") to "October"),
        Month.NOVEMBER to mapOf(Locale("ru") to "Ноябрь", Locale("en") to "November"),
        Month.DECEMBER to mapOf(Locale("ru") to "Декабрь", Locale("en") to "December")
    )

    fun getMonthName(month: Month, locale: Locale): String {
        return monthNames[month]?.get(locale) ?: ""
    }
}

object DayOfWeekLocalizations{
    private val dayOfWeekNames: Map<DayOfWeek, Map<Locale, String>> = mapOf(
        DayOfWeek.MONDAY to mapOf(Locale("ru") to "Понедельник", Locale("en") to "Monday"),
        DayOfWeek.TUESDAY to mapOf(Locale("ru") to "Вторник", Locale("en") to "Tuesday"),
        DayOfWeek.WEDNESDAY to mapOf(Locale("ru") to "Среда", Locale("en") to "Wednesday"),
        DayOfWeek.THURSDAY to mapOf(Locale("ru") to "Четверг", Locale("en") to "Thursday"),
        DayOfWeek.FRIDAY to mapOf(Locale("ru") to "Пятница", Locale("en") to "Friday"),
        DayOfWeek.SATURDAY to mapOf(Locale("ru") to "Суббота", Locale("en") to "Saturday"),
        DayOfWeek.SUNDAY to mapOf(Locale("ru") to "Воскресенье", Locale("en") to "Sunday")
    )

    fun getDayOfWeek(dayOfWeek: DayOfWeek, locale: Locale) : String{
        return dayOfWeekNames[dayOfWeek]?.get(locale) ?: ""
    }
}