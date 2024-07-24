package office.effective.features.booking.converters

import com.google.api.client.util.DateTime

/**
 * Converts local time to [DateTime].
 *
 * Use it for all requests to Google Calendar.
 */
fun Long.toDateTime(): DateTime {
    return DateTime(this)
}
