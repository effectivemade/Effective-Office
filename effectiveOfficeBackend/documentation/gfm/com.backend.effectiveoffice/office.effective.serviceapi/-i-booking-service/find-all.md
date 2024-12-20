//[com.backend.effectiveoffice](../../../index.md)/[office.effective.serviceapi](../index.md)/[IBookingService](index.md)/[findAll](find-all.md)

# findAll

[jvm]\
abstract fun [findAll](find-all.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null, workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null, bookingRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, bookingRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;

Returns all bookings. Bookings can be filtered by owner and workspace id

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| userId | use to filter by booking owner id |
| workspaceId | use to filter by booking workspace id |
| bookingRangeTo | upper bound (exclusive) for a beginBooking to filter by. Optional. |
| bookingRangeFrom | lower bound (exclusive) for a endBooking to filter by. |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if [UserModel](../../office.effective.model/-user-model/index.md) or [Workspace](../../office.effective.model/-workspace/index.md) with the given id doesn't exist in database |
