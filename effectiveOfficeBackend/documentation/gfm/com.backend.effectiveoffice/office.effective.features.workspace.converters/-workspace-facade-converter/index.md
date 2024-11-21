//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.converters](../index.md)/[WorkspaceFacadeConverter](index.md)

# WorkspaceFacadeConverter

[jvm]\
class [WorkspaceFacadeConverter](index.md)(uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md))

Converts between [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) (with contained [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md) and [UtilityDTO](../../office.effective.dto/-utility-d-t-o/index.md)s) and [Workspace](../../office.effective.model/-workspace/index.md) (with contained [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) and [Utilities](../../office.effective.model/-utility/index.md)) objects.

This converter helps in the transformation of data between the DTO and model representations of a workspace.

## Constructors

| | |
|---|---|
| [WorkspaceFacadeConverter](-workspace-facade-converter.md) | [jvm]<br>constructor(uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [dtoToModel](dto-to-model.md) | [jvm]<br>fun [dtoToModel](dto-to-model.md)(dto: [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)): [Workspace](../../office.effective.model/-workspace/index.md)<br>Converts [Workspace](../../office.effective.model/-workspace/index.md) with [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) and [Utilities](../../office.effective.model/-utility/index.md) to [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md) and [UtilityDTO](../../office.effective.dto/-utility-d-t-o/index.md)s. Uses [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md) to convert workspace id to UUID, but if [WorkspaceDTO.id](../../office.effective.dto/-workspace-d-t-o/id.md)==&quot;null&quot; [Workspace.id](../../office.effective.model/-workspace/id.md) will be null |
| [modelToDto](model-to-dto.md) | [jvm]<br>fun [modelToDto](model-to-dto.md)(model: [Workspace](../../office.effective.model/-workspace/index.md)): [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)<br>Converts [Workspace](../../office.effective.model/-workspace/index.md) with [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) and [Utilities](../../office.effective.model/-utility/index.md) to [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md) and [UtilityDTO](../../office.effective.dto/-utility-d-t-o/index.md)s |
| [zoneModelToDto](zone-model-to-dto.md) | [jvm]<br>fun [zoneModelToDto](zone-model-to-dto.md)(model: [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)): [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md)<br>Converts [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) to [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md) |
