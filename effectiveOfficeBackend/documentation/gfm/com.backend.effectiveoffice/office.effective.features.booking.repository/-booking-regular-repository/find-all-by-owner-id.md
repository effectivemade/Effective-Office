//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingRegularRepository](index.md)/[findAllByOwnerId](find-all-by-owner-id.md)

# findAllByOwnerId

[jvm]\
open override fun [findAllByOwnerId](find-all-by-owner-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Returns all bookings with the given owner id

#### Return

List of all user [Booking](../../office.effective.model/-booking/index.md)

#### Author

Daniil Zavyalov

#### Parameters

jvm

| |
|---|
| ownerId |
| eventRangeFrom | lower bound (exclusive) for a endBooking to filter by. Old Google calendar events may not appear correctly in the system and cause unexpected exceptions |
| eventRangeTo | upper bound (exclusive) for a beginBooking to filter by. Optional. |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if user with the given id doesn't exist in database |
