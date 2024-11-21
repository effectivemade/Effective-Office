//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.facade](../index.md)/[BookingFacade](index.md)/[findById](find-by-id.md)

# findById

[jvm]\
fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)

Retrieves a booking model by its id

#### Return

[BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) with the given id

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| id | id of requested booking |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if booking with the given id doesn't exist in database |
