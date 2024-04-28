//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)/[toGoogleWorkspaceMeetingEvent](to-google-workspace-meeting-event.md)

# toGoogleWorkspaceMeetingEvent

[jvm]\
fun [toGoogleWorkspaceMeetingEvent](to-google-workspace-meeting-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event

Converts meeting workspace [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application. Event.summary is used to indicate the booking workspace.

#### Return

The resulting Event object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| model | [Booking](../../office.effective.model/-booking/index.md) to be converted |
