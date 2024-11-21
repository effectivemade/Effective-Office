//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingRepository](index.md)/[save](save.md)

# save

[jvm]\
fun [save](save.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)

Saves a given booking. If given model will have an id, it will be ignored. Use the returned model for further operations

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
| [WorkspaceUnavailableException](../../office.effective.common.exception/-workspace-unavailable-exception/index.md) | if workspace can't be booked in a given period |
