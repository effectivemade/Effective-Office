//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingCalendarRepository](index.md)

# BookingCalendarRepository

[jvm]\
class [BookingCalendarRepository](index.md)(calendarIdsRepository: [CalendarIdsRepository](../../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), calendar: Calendar, googleCalendarConverter: [GoogleCalendarConverter](../../office.effective.features.booking.converters/-google-calendar-converter/index.md)) : [IBookingRepository](../-i-booking-repository/index.md)

Class that executes Google calendar queries for booking meeting rooms

Filters out all events that have a start less than the calendar.minTime from application.conf

## Constructors

| | |
|---|---|
| [BookingCalendarRepository](-booking-calendar-repository.md) | [jvm]<br>constructor(calendarIdsRepository: [CalendarIdsRepository](../../office.effective.features.calendar.repository/-calendar-ids-repository/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), calendar: Calendar, googleCalendarConverter: [GoogleCalendarConverter](../../office.effective.features.booking.converters/-google-calendar-converter/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | [jvm]<br>open override fun [deleteById](delete-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Deletes the booking with the given id |
| [existsById](exists-by-id.md) | [jvm]<br>open override fun [existsById](exists-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a booking with the given id exists |
| [findAll](find-all.md) | [jvm]<br>open override fun [findAll](find-all.md)(eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Retrieves all bookings |
| [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md) | [jvm]<br>open override fun [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given workspace and owner id |
| [findAllByOwnerId](find-all-by-owner-id.md) | [jvm]<br>open override fun [findAllByOwnerId](find-all-by-owner-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given owner id |
| [findAllByWorkspaceId](find-all-by-workspace-id.md) | [jvm]<br>open override fun [findAllByWorkspaceId](find-all-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), eventRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), eventRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given workspace id |
| [findById](find-by-id.md) | [jvm]<br>open override fun [findById](find-by-id.md)(bookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Booking](../../office.effective.model/-booking/index.md)?<br>Retrieves a booking model by its id. Retrieved booking contains user and workspace models without integrations and utilities |
| [save](save.md) | [jvm]<br>open override fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Saves a given booking. If given model will have an id, it will be ignored. Use the returned model for further operations |
| [update](update.md) | [jvm]<br>open override fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Updates a given booking. Use the returned model for further operations |
