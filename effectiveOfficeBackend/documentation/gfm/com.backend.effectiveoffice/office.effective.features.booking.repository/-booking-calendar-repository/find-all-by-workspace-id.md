//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingCalendarRepository](index.md)/[findAllByWorkspaceId](find-all-by-workspace-id.md)

# findAllByWorkspaceId

[jvm]\
open override fun [findAllByWorkspaceId](find-all-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Returns all bookings with the given workspace id

#### Return

List of all workspace [Booking](../../office.effective.model/-booking/index.md)

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| |
|---|
| workspaceId |
| eventRangeFrom | lower bound (exclusive) for a endBooking to filter by. Old Google calendar events may not appear correctly in the system and cause unexpected exceptions |
| eventRangeTo | upper bound (exclusive) for a beginBooking to filter by. Optional. |
