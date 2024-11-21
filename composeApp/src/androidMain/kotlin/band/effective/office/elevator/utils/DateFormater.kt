package band.effective.office.elevator.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

actual fun getFormattedDate(date: String, format: String): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    val formDate = LocalDate.parse(date, formatter)
    return formDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
}