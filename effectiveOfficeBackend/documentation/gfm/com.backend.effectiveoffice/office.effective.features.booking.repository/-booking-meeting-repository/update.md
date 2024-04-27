//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.repository](../index.md)/[BookingMeetingRepository](index.md)/[update](update.md)

# update

[jvm]\
open override fun [update](update.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [Booking](../../office.effective.model/-booking/index.md)

Updates a given booking. Use the returned model for further operations

#### Return

[Booking](../../office.effective.model/-booking/index.md) after change saving

#### Parameters

jvm

| | |
|---|---|
| booking | changed booking |

#### Throws

| | |
|---|---|
| [MissingIdException](../../office.effective.common.exception/-missing-id-exception/index.md) | if [Booking.id](../../office.effective.model/-booking/id.md) or [Booking.workspace](../../office.effective.model/-booking/workspace.md).id is null |
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if booking given id doesn't exist in the database |
| [WorkspaceUnavailableException](../../office.effective.common.exception/-workspace-unavailable-exception/index.md) | if booking unavailable because of collision check |
