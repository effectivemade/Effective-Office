//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.converters](../index.md)/[WorkspaceRepositoryConverter](index.md)/[modelToEntity](model-to-entity.md)

# modelToEntity

[jvm]\
fun [modelToEntity](model-to-entity.md)(model: [Workspace](../../office.effective.model/-workspace/index.md)): [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md)

Converts [Workspace](../../office.effective.model/-workspace/index.md) to [WorkspaceEntity](../../office.effective.features.workspace.repository/-workspace-entity/index.md) with random UUID. Use database connection to find [WorkspaceTagEntity](../../office.effective.features.workspace.repository/-workspace-tag-entity/index.md) by [Workspace.tag](../../office.effective.model/-workspace/tag.md)

#### Author

Daniil Zavyalov

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if tag doesn't exist in the database |
