//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.service](../index.md)/[BookingService](index.md)/[save](save.md)

# save

[jvm]\
open override fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)

Saves a given booking. Use the returned model for further operations

#### Return

saved [Booking](../../office.effective.model/-booking/index.md)

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| booking | [Booking](../../office.effective.model/-booking/index.md) to be saved |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if workspace with the given id not found |
