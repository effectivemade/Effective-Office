//[com.backend.effectiveoffice](../../../index.md)/[office.effective.serviceapi](../index.md)/[IBookingService](index.md)

# IBookingService

interface [IBookingService](index.md)

Interface that provides methods for manipulating [Booking](../../office.effective.model/-booking/index.md) objects.

#### Inheritors

| |
|---|
| [BookingService](../../office.effective.features.booking.service/-booking-service/index.md) |

## Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | [jvm]<br>abstract fun [deleteById](delete-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Deletes the booking with the given id |
| [existsById](exists-by-id.md) | [jvm]<br>abstract fun [existsById](exists-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a booking with the given id exists |
| [findAll](find-all.md) | [jvm]<br>abstract fun [findAll](find-all.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null, workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)? = null, bookingRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)? = null, bookingRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings. Bookings can be filtered by owner and workspace id |
| [findById](find-by-id.md) | [jvm]<br>abstract fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Booking](../../office.effective.model/-booking/index.md)?<br>Retrieves a booking model by its id |
| [save](save.md) | [jvm]<br>abstract fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Saves a given booking. Use the returned model for further operations |
| [update](update.md) | [jvm]<br>abstract fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Updates a given booking. Use the returned model for further operations |
