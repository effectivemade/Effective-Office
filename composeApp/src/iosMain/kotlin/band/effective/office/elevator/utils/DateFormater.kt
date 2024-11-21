package band.effective.office.elevator.utils

import kotlinx.cinterop.objcPtr
import platform.Foundation.*

actual fun getFormattedDate(date: String, format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.timeZone = NSTimeZone.localTimeZone
    dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
    dateFormatter.dateFormat = format
    val formDate = dateFormatter.dateFromString(date)
    val isoFormatter = NSDateFormatter()
    isoFormatter.dateFormat = "yyyy-MM-dd"
    return isoFormatter.stringFromDate(formDate!!)
}