//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.converters](../index.md)/[WorkspaceFacadeConverter](index.md)/[dtoToModel](dto-to-model.md)

# dtoToModel

[jvm]\
fun [dtoToModel](dto-to-model.md)(dto: [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)): [Workspace](../../office.effective.model/-workspace/index.md)

Converts [Workspace](../../office.effective.model/-workspace/index.md) with [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) and [Utilities](../../office.effective.model/-utility/index.md) to [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with [WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md) and [UtilityDTO](../../office.effective.dto/-utility-d-t-o/index.md)s. Uses [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md) to convert workspace id to UUID, but if [WorkspaceDTO.id](../../office.effective.dto/-workspace-d-t-o/id.md)==&quot;null&quot; [Workspace.id](../../office.effective.model/-workspace/id.md) will be null

#### Return

The resulting Workspace object

#### Author

Daniil Zavyalov, Danil Kiselev

#### Parameters

jvm

| | |
|---|---|
| dto | The WorkspaceDTO object to be converted |
