//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.facade](../index.md)/[BookingFacade](index.md)

# BookingFacade

[jvm]\
class [~~BookingFacade~~](index.md)(bookingService: [IBookingService](../../office.effective.serviceapi/-i-booking-service/index.md), transactionManager: [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md), bookingConverter: [BookingDtoModelConverter](../../office.effective.features.booking.converters/-booking-dto-model-converter/index.md))---

### Deprecated

Deprecated since 1.0 api version

#### Replace with

```kotlin
import office.effective.features.booking.facade.BookingFacadeV1

```
```kotlin
BookingFacadeV1
```
---

Class used in routes to handle bookings requests. Provides business transaction, data conversion and validation.

In case of an error, the database transaction will be rolled back.

## Constructors

| | |
|---|---|
| [BookingFacade](-booking-facade.md) | [jvm]<br>constructor(bookingService: [IBookingService](../../office.effective.serviceapi/-i-booking-service/index.md), transactionManager: [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md), bookingConverter: [BookingDtoModelConverter](../../office.effective.features.booking.converters/-booking-dto-model-converter/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [deleteById](delete-by-id.md) | [jvm]<br>fun [deleteById](delete-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Deletes the booking with the given id |
| [findAll](find-all.md) | [jvm]<br>fun [findAll](find-all.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, workspaceId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, bookingRangeTo: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)?, bookingRangeFrom: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) = BookingConstants.MIN_SEARCH_START_TIME): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)&gt;<br>Returns all bookings. Bookings can be filtered by owner and workspace id |
| [findById](find-by-id.md) | [jvm]<br>fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)<br>Retrieves a booking model by its id |
| [post](post.md) | [jvm]<br>fun [post](post.md)(bookingDTO: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)<br>Saves a given booking. Use the returned model for further operations |
| [put](put.md) | [jvm]<br>fun [put](put.md)(bookingDTO: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)<br>Updates a given booking. Use the returned model for further operations |
