//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[IBookingRepository](index.md)/[update](update.md)

# update

[jvm]\
abstract fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)

Updates a given booking. Use the returned model for further operations

#### Return

[Booking](../../office.effective.model/-booking/index.md) after change saving

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| booking | changed booking |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if booking given id doesn't exist in the database |
