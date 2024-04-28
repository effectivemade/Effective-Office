//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.facade](../index.md)/[BookingFacadeV1](index.md)/[findById](find-by-id.md)

# findById

[jvm]\
fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md)

Retrieves a booking model by its id

#### Return

[BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md) with the given id

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
