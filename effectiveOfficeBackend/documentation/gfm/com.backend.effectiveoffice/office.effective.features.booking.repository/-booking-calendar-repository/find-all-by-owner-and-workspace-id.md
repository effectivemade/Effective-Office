//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingCalendarRepository](index.md)/[findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md)

# findAllByOwnerAndWorkspaceId

[jvm]\
open override fun [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Returns all bookings with the given workspace and owner id

#### Return

List of all [Booking](../../office.effective.model/-booking/index.md)s with the given workspace and owner id

#### Author

Daniil Zavyalov

#### Parameters

jvm

| |
|---|
| ownerId |
| workspaceId |
| eventRangeFrom | lower bound (exclusive) for a endBooking to filter by. Old Google calendar events may not appear correctly in the system and cause unexpected exceptions |
| eventRangeTo | upper bound (exclusive) for a beginBooking to filter by. Optional. |
