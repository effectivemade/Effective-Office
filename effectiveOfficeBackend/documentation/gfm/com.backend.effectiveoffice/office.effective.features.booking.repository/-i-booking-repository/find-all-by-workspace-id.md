//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[IBookingRepository](index.md)/[findAllByWorkspaceId](find-all-by-workspace-id.md)

# findAllByWorkspaceId

[jvm]\
abstract fun [findAllByWorkspaceId](find-all-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

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
| eventRangeFrom | use to set an upper bound for filtering bookings by start time |
| eventRangeTo | lover bound for filtering bookings by start time |
