//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.booking.converters](../index.md)/[BookingDtoModelConverter](index.md)

# BookingDtoModelConverter

[jvm]\
class [BookingDtoModelConverter](index.md)(userConverter: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), workspaceConverter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md))

Converts between [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) and [Booking](../../office.effective.model/-booking/index.md)

Uses [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md) and [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md) to convert contained users and workspaces

## Constructors

| | |
|---|---|
| [BookingDtoModelConverter](-booking-dto-model-converter.md) | [jvm]<br>constructor(userConverter: [UserDTOModelConverter](../../office.effective.features.user.converters/-user-d-t-o-model-converter/index.md), workspaceConverter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md), userRepository: [UserRepository](../../office.effective.features.user.repository/-user-repository/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [dtoToModel](dto-to-model.md) | [jvm]<br>fun [~~dtoToModel~~](dto-to-model.md)(bookingDTO: [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)): [Booking](../../office.effective.model/-booking/index.md)<br>Converts [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to [Booking](../../office.effective.model/-booking/index.md) |
| [modelToDto](model-to-dto.md) | [jvm]<br>fun [~~modelToDto~~](model-to-dto.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md)<br>Converts [Booking](../../office.effective.model/-booking/index.md) to [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) |
| [modelToResponseDto](model-to-response-dto.md) | [jvm]<br>fun [modelToResponseDto](model-to-response-dto.md)(booking: [Booking](../../office.effective.model/-booking/index.md)): [BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md)<br>Converts [Booking](../../office.effective.model/-booking/index.md) to [BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md) |
| [requestDtoToModel](request-dto-to-model.md) | [jvm]<br>fun [requestDtoToModel](request-dto-to-model.md)(bookingDto: [BookingRequestDTO](../../office.effective.dto/-booking-request-d-t-o/index.md), id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): [Booking](../../office.effective.model/-booking/index.md)<br>Converts [BookingDTO](../../office.effective.dto/-booking-d-t-o/index.md) to [Booking](../../office.effective.model/-booking/index.md). Users and workspace will be retrieved from database |
