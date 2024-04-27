//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.service](../index.md)/[BookingService](index.md)

# BookingService

[jvm]\
class [BookingService](index.md)(bookingMeetingRepository: [BookingMeetingRepository](../../office.effective.features.booking.repository/-booking-meeting-repository/index.md), bookingRegularRepository: [BookingRegularRepository](../../office.effective.features.booking.repository/-booking-regular-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) : [IBookingService](../../office.effective.serviceapi/-i-booking-service/index.md)

Class that implements the [IBookingService](../../office.effective.serviceapi/-i-booking-service/index.md) methods

Uses [BookingMeetingRepository](../../office.effective.features.booking.repository/-booking-meeting-repository/index.md) for operations with meeting rooms. Uses [BookingRegularRepository](../../office.effective.features.booking.repository/-booking-regular-repository/index.md) for operations with workspaces.

## Constructors

| | |
|---|---|
| [BookingService](-booking-service.md) | [jvm]<br>constructor(bookingMeetingRepository: [BookingMeetingRepository](../../office.effective.features.booking.repository/-booking-meeting-repository/index.md), bookingRegularRepository: [BookingRegularRepository](../../office.effective.features.booking.repository/-booking-regular-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | [jvm]<br>open override fun [deleteById](delete-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Deletes the booking with the given id |
| [existsById](exists-by-id.md) | [jvm]<br>open override fun [existsById](exists-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a booking with the given id exists |
| [findAll](find-all.md) | [jvm]<br>open override fun [findAll](find-all.md)(userId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)?, workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)?, bookingRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, bookingRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings. Bookings can be filtered by owner and workspace id |
| [findById](find-by-id.md) | [jvm]<br>open override fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Booking](../../office.effective.model/-booking/index.md)?<br>Retrieves a booking model by its id |
| [save](save.md) | [jvm]<br>open override fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Saves a given booking. Use the returned model for further operations |
| [update](update.md) | [jvm]<br>open override fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Updates a given booking. Use the returned model for further operations |
