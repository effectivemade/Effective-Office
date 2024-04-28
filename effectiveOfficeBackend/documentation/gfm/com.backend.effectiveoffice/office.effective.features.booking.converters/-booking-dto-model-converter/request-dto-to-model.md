//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingDtoModelConverter](index.md)/[requestDtoToModel](request-dto-to-model.md)

# requestDtoToModel

[jvm]\
fun [requestDtoToModel](request-dto-to-model.md)(bookingDto: [BookingRequestDTO](../../office.effective.dto/-booking-request-d-t-o/index.md), id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): [Booking](../../office.effective.model/-booking/index.md)

Converts [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to [Booking](../../office.effective.model/-booking/index.md). Users and workspace will be retrieved from database

#### Return

The resulting [Booking](../../office.effective.model/-booking/index.md) object

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| bookingDto | [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to be converted |
| id | booking id |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if user with the given email or workspace with the given id doesn't exist in database |
