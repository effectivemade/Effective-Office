package office.effective.features.booking.converters

import com.google.api.client.util.DateTime
import office.effective.common.constants.BookingConstants

/**
 * Converts local time to Google [DateTime] in GMT.
 *
 * Use it for all requests to Google Calendar.
 */
fun Long.toGoogleDateTime(): DateTime {
    return DateTime(this - BookingConstants.DEFAULT_TIMEZONE_OFFSET_MILLIS)
}