//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[GoogleCalendarConverter](index.md)/[toBookingDTO](to-booking-d-t-o.md)

# toBookingDTO

[jvm]\
fun [toBookingDTO](to-booking-d-t-o.md)(event: Event): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)

Converts Event to [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)

Creates placeholders if workspace, owner or participant doesn't exist in database

#### Return

The resulting [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) object

#### Author

Danil Kiselev, Max Mishenko, Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| event | Event to be converted |
