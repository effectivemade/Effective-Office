package office.effective.common.constants

import office.effective.config
import org.threeten.bp.LocalDateTime
import java.time.Instant
import java.util.*

/**
 * Constants for booking
 */
object BookingConstants {
    /**
     * Minimum booking start time.
     * Bookings that started earlier should be filtered out in requests.
     */
    val MIN_SEARCH_START_TIME = config.propertyOrNull("calendar.minTime")?.getString()?.toLong()
        ?: throw Exception("Config file does not contain minimum time")
    val DEFAULT_CALENDAR: String = System.getenv("DEFAULT_CALENDAR")
        ?: config.propertyOrNull("calendar.defaultCalendar")?.getString()
        ?: throw Exception("Environment and config file does not contain Google default calendar id")
    val REGULAR_WORKSPACES_CALENDAR: String = System.getenv("WORKSPACE_CALENDAR")
        ?: config.propertyOrNull("calendar.workspaceCalendar")?.getString()
        ?: throw Exception("Environment and config file does not contain workspace Google calendar id")
    val DEFAULT_TIMEZONE_ID: String = config.propertyOrNull("calendar.defaultTimezone")?.getString()
        ?: throw Exception("Config file does not contain default timezone id")
    val DEFAULT_TIMEZONE_OFFSET_MILLIS: Long = TimeZone.getTimeZone(DEFAULT_TIMEZONE_ID)
        .getOffset(Instant.now().toEpochMilli())
        .toLong()
    const val UNTIL_FORMAT = "yyyyMMdd"
}
