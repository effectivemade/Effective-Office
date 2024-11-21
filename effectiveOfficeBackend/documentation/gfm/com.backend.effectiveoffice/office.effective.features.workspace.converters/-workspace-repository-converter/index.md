//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.converters](../index.md)/[WorkspaceRepositoryConverter](index.md)

# WorkspaceRepositoryConverter

[jvm]\
class [WorkspaceRepositoryConverter](index.md)(database: Database)

Converts between [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md) and [Workspace](../../office.effective.model/-workspace/index.md) objects.

This converter helps in the transformation of data between the entity and model representations of a workspace.

## Constructors

| | |
|---|---|
| [WorkspaceRepositoryConverter](-workspace-repository-converter.md) | [jvm]<br>constructor(database: Database) |

## Functions

| Name | Summary |
|---|---|
| [entityToModel](entity-to-model.md) | [jvm]<br>fun [entityToModel](entity-to-model.md)(entity: [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md), utilities: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Utility](../../office.effective.model/-utility/index.md)&gt;): [Workspace](../../office.effective.model/-workspace/index.md)<br>Converts [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md) with its [WorkspaceZoneEntity](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md) to [Workspace](../../office.effective.model/-workspace/index.md) with [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) |
| [modelToEntity](model-to-entity.md) | [jvm]<br>fun [modelToEntity](model-to-entity.md)(model: [Workspace](../../office.effective.model/-workspace/index.md)): [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md)<br>Converts [Workspace](../../office.effective.model/-workspace/index.md) to [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md) with random UUID. Use database connection to find [WorkspaceTagEntity](../../office.effective.features.workspace.repository/-workspace-tag-entity/index.md) by [Workspace.tag](../../office.effective.model/-workspace/tag.md) |
| [utilityEntityToModel](utility-entity-to-model.md) | [jvm]<br>fun [utilityEntityToModel](utility-entity-to-model.md)(entity: [UtilityEntity](../../office.effective.features.workspace.repository/-utility-entity/index.md), count: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Utility](../../office.effective.model/-utility/index.md)<br>Converts [UtilityEntity](../../office.effective.features.workspace.repository/-utility-entity/index.md) to [Utility](../../office.effective.model/-utility/index.md) with given count |
| [zoneEntityToModel](zone-entity-to-model.md) | [jvm]<br>fun [zoneEntityToModel](zone-entity-to-model.md)(zoneEntity: [WorkspaceZoneEntity](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md)): [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)<br>Converts [WorkspaceZoneEntity](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md) to [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) model |
| [zoneModelToEntity](zone-model-to-entity.md) | [jvm]<br>fun [zoneModelToEntity](zone-model-to-entity.md)(zoneModel: [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)): [WorkspaceZoneEntity](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md)<br>Converts [WorkspaceZoneEntity](../../office.effective.features.workspace.repository/-workspace-zone-entity/index.md) to [WorkspaceZone](../../office.effective.model/-workspace-zone/index.md) model |
