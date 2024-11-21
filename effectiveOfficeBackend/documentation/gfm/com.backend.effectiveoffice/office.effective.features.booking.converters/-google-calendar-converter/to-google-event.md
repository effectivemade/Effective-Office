//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)/[toGoogleEvent](to-google-event.md)

# toGoogleEvent

[jvm]\
fun [toGoogleEvent](to-google-event.md)(dto: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): Event

Converts [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application

#### Return

The resulting Event object

#### Author

Danil Kiselev, Max Mishenko

#### Parameters

jvm

| | |
|---|---|
| dto | [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to be converted |

[jvm]\
fun [toGoogleEvent](to-google-event.md)(model: [Booking](../../office.effective.model/-booking/index.md)): Event

Converts meeting room [Booking](../../office.effective.model/-booking/index.md) to Event. Event.description is used to indicate the booking author, because Event.organizer is defaultAccount of application

#### Return

The resulting Event object

#### Author

Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| model | [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to be converted |
