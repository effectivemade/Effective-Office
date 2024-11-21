//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingFacadeConverter](index.md)

# BookingFacadeConverter

[jvm]\
class [BookingFacadeConverter](index.md)(userConverter: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), workspaceConverter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md))

Converts between [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) and [Booking](../../office.effective.model/-booking/index.md)

Uses [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md) and [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md) to convert contained users and workspaces

## Constructors

| | |
|---|---|
| [BookingFacadeConverter](-booking-facade-converter.md) | [jvm]<br>constructor(userConverter: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), workspaceConverter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [dtoToModel](dto-to-model.md) | [jvm]<br>fun [dtoToModel](dto-to-model.md)(bookingDTO: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Converts [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to [Booking](../../office.effective.model/-booking/index.md) |
| [modelToDto](model-to-dto.md) | [jvm]<br>fun [modelToDto](model-to-dto.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)<br>Converts [Booking](../../office.effective.model/-booking/index.md) to [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) |
