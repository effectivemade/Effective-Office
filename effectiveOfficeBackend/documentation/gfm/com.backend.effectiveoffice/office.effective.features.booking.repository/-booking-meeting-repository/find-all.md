//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingMeetingRepository](index.md)/[findAll](find-all.md)

# findAll

[jvm]\
open override fun [findAll](find-all.md)(eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Retrieves all bookings

#### Return

All [Booking](../../office.effective.model/-booking/index.md)s

#### Parameters

jvm

| | |
|---|---|
| eventRangeFrom | lower bound (exclusive) for a endBooking to filter by. Old Google calendar events may not appear correctly in the system and cause unexpected exceptions |
| eventRangeTo | upper bound (exclusive) for a beginBooking to filter by. Optional. |
