//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.converters](../index.md)/[WorkspaceRepositoryConverter](index.md)/[entityToModel](entity-to-model.md)

# entityToModel

[jvm]\
fun [entityToModel](entity-to-model.md)(entity: [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md), utilities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Utility](../../office.effective.model/-utility/index.md)&gt;): [Workspace](../../office.effective.model/-workspace/index.md)

Converts [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md) with its [WorkspaceZoneEntity](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md) to [Workspace](../../office.effective.model/-workspace/index.md) with [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)

#### Return

The resulting [Workspace](../../office.effective.model/-workspace/index.md) object

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| entity | The [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md) object to be converted |
| utilities | [Utilities](../../office.effective.model/-utility/index.md) for [Workspace](../../office.effective.model/-workspace/index.md) |
