//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)/[toGoogleWorkspaceEvent](to-google-workspace-event.md)

# toGoogleWorkspaceEvent

[jvm]\
fun [toGoogleWorkspaceEvent](to-google-workspace-event.md)(dto: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): Event

Converts workspace [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace.

#### Return

The resulting Event object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| dto | [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to be converted |

[jvm]\
fun [toGoogleWorkspaceEvent](to-google-workspace-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event

Converts workspace [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace.

#### Return

The resulting Event object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| model | [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to be converted |
