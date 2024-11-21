//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingRepository](index.md)

# BookingRepository

[jvm]\
class [BookingRepository](index.md)(database: Database, converter: [BookingRepositoryConverter](../../office.effective.features.booking.converters/-booking-repository-converter/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md))

Class that executes database queries with bookings

All WorkspaceRepository methods return [Booking](../../office.effective.model/-booking/index.md) objects with [Workspace](../../office.effective.model/-workspace/index.md) and [UserModel](../../office.effective.model/-user-model/index.md) objects with empty [Workspace.utilities](../../office.effective.model/-workspace/utilities.md) and [UserModel.integrations](../../office.effective.model/-user-model/integrations.md)

TODO: should implements IBookingRepository interface (add minStartTime and maxStartTime to queries)

## Constructors

| | |
|---|---|
| [BookingRepository](-booking-repository.md) | [jvm]<br>constructor(database: Database, converter: [BookingRepositoryConverter](../../office.effective.features.booking.converters/-booking-repository-converter/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | [jvm]<br>fun [deleteById](delete-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Deletes the booking with the given id |
| [existsById](exists-by-id.md) | [jvm]<br>fun [existsById](exists-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a booking with the given id exists |
| [findAll](find-all.md) | [jvm]<br>fun [findAll](find-all.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Retrieves all bookings |
| [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md) | [jvm]<br>fun [findAllByOwnerAndWorkspaceId](find-all-by-owner-and-workspace-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given workspace and owner id |
| [findAllByOwnerId](find-all-by-owner-id.md) | [jvm]<br>fun [findAllByOwnerId](find-all-by-owner-id.md)(ownerId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given owner id |
| [findAllByWorkspaceId](find-all-by-workspace-id.md) | [jvm]<br>fun [findAllByWorkspaceId](find-all-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Booking](../../office.effective.model/-booking/index.md)&gt;<br>Returns all bookings with the given workspace id |
| [findById](find-by-id.md) | [jvm]<br>fun [findById](find-by-id.md)(bookingId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Booking](../../office.effective.model/-booking/index.md)?<br>Retrieves a booking model by its id. Retrieved booking contains user and workspace models without integrations and utilities |
| [save](save.md) | [jvm]<br>fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Saves a given booking. If given model will have an id, it will be ignored. Use the returned model for further operations |
| [update](update.md) | [jvm]<br>fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Updates a given booking. Use the returned model for further operations |
