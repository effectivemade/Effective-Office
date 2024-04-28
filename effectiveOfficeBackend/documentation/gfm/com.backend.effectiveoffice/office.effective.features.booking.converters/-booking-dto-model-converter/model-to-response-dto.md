//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingDtoModelConverter](index.md)/[modelToResponseDto](model-to-response-dto.md)

# modelToResponseDto

[jvm]\
fun [modelToResponseDto](model-to-response-dto.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md)

Converts [Booking](../../office.effective.model/-booking/index.md) to [BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md)

#### Return

The resulting [BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md) object

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| booking | [Booking](../../office.effective.model/-booking/index.md) to be converted |

#### Throws

| | |
|---|---|
| [MissingIdException](../../office.effective.common.exception/-missing-id-exception/index.md) | if [booking](model-to-response-dto.md) doesn't contain an id |
