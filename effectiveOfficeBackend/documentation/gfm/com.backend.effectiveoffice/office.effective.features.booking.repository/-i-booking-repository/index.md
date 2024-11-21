//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[IBookingRepository](index.md)

# IBookingRepository

interface [IBookingRepository](index.md)

Interface of repository to manipulate with workspace bookings

#### Inheritors

| |
|---|
| [BookingCalendarRepository](../-booking-calendar-repository/index.md) |
| [BookingWorkspaceRepository](../-booking-workspace-repository/index.md) |

## Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | [jvm]<br>abstract fun [deleteById](delete-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Deletes the booking with the given id |
| [existsById](exists-by-id.md) | [jvm]<br>abstract fun [existsById](exists-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a booking with the given id exists |
| [findAll](find-all.md) | [jvm]<br>abstract fun [findAll](find-all.md)(eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Retrieves all bookings |
| [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md) | [jvm]<br>abstract fun [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given workspace and owner id |
| [findAllByOwnerId](find-all-by-owner-id.md) | [jvm]<br>abstract fun [findAllByOwnerId](find-all-by-owner-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given owner id |
| [findAllByWorkspaceId](find-all-by-workspace-id.md) | [jvm]<br>abstract fun [findAllByWorkspaceId](find-all-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given workspace id |
| [findById](find-by-id.md) | [jvm]<br>abstract fun [findById](find-by-id.md)(bookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Booking](../../office.effective.model/-booking/index.md)?<br>Retrieves a booking model by its id. Retrieved booking contains user and workspace models without integrations and utilities |
| [save](save.md) | [jvm]<br>abstract fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Saves a given booking. If given model will have an id, it will be ignored. Use the returned model for further operations |
| [update](update.md) | [jvm]<br>abstract fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Updates a given booking. Use the returned model for further operations |
