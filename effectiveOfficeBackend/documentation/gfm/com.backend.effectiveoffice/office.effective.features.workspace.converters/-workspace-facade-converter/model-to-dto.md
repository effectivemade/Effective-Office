//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.converters](../index.md)/[WorkspaceFacadeConverter](index.md)/[modelToDto](model-to-dto.md)

# modelToDto

[jvm]\
fun [modelToDto](model-to-dto.md)(model: [Workspace](../../office.effective.model/-workspace/index.md), bookings: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[BookingResponseDTO](../../office.effective.dto/-booking-response-d-t-o/index.md)&gt;? = null): [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)

Converts [Workspace](../../office.effective.model/-workspace/index.md) with [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) and [Utilities](../../office.effective.model/-utility/index.md) to [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md) and [UtilityDTO](../../office.effective.dto/-utility-d-t-o/index.md)s

#### Return

The resulting [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| model | The [Workspace](../../office.effective.model/-workspace/index.md) object to be converted |
| bookings | bookings of this workspace |
