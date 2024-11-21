//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)/[toWorkspaceBooking](to-workspace-booking.md)

# toWorkspaceBooking

[jvm]\
fun [toWorkspaceBooking](to-workspace-booking.md)(event: Event): [Booking](../../office.effective.model/-booking/index.md)

Converts Event to [Booking](../../office.effective.model/-booking/index.md)

Creates placeholders if workspace or owner doesn't exist in database

#### Return

The resulting [Booking](../../office.effective.model/-booking/index.md) object

#### Author

Danil Kiselev, Max Mishenko, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| event | Event to be converted |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | if it fails to get user id from description or workspace id from summary of Google Calendar event |
