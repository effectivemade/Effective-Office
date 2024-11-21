//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.workspace.repository](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [Utilities](-utilities/index.md) | [jvm]<br>object [Utilities](-utilities/index.md) : Table&lt;[UtilityEntity](-utility-entity/index.md)&gt; |
| [UtilityEntity](-utility-entity/index.md) | [jvm]<br>interface [UtilityEntity](-utility-entity/index.md) : Entity&lt;[UtilityEntity](-utility-entity/index.md)&gt; |
| [WorkspaceEntity](-workspace-entity/index.md) | [jvm]<br>interface [WorkspaceEntity](-workspace-entity/index.md) : Entity&lt;[WorkspaceEntity](-workspace-entity/index.md)&gt; |
| [WorkspaceRepository](-workspace-repository/index.md) | [jvm]<br>class [WorkspaceRepository](-workspace-repository/index.md)(database: Database, converter: [WorkspaceRepositoryConverter](../office.effective.features.workspace.converters/-workspace-repository-converter/index.md))<br>Class that executes database queries with workspaces |
| [Workspaces](-workspaces/index.md) | [jvm]<br>object [Workspaces](-workspaces/index.md) : Table&lt;[WorkspaceEntity](-workspace-entity/index.md)&gt; |
| [WorkspaceTagEntity](-workspace-tag-entity/index.md) | [jvm]<br>interface [WorkspaceTagEntity](-workspace-tag-entity/index.md) : Entity&lt;[WorkspaceTagEntity](-workspace-tag-entity/index.md)&gt; |
| [WorkspaceTags](-workspace-tags/index.md) | [jvm]<br>object [WorkspaceTags](-workspace-tags/index.md) : Table&lt;[WorkspaceTagEntity](-workspace-tag-entity/index.md)&gt; |
| [WorkspaceUtilities](-workspace-utilities/index.md) | [jvm]<br>object [WorkspaceUtilities](-workspace-utilities/index.md) : Table&lt;[WorkspaceUtilityEntity](-workspace-utility-entity/index.md)&gt; |
| [WorkspaceUtilityEntity](-workspace-utility-entity/index.md) | [jvm]<br>interface [WorkspaceUtilityEntity](-workspace-utility-entity/index.md) : Entity&lt;[WorkspaceUtilityEntity](-workspace-utility-entity/index.md)&gt; |
| [WorkspaceZoneEntity](-workspace-zone-entity/index.md) | [jvm]<br>interface [WorkspaceZoneEntity](-workspace-zone-entity/index.md) : Entity&lt;[WorkspaceZoneEntity](-workspace-zone-entity/index.md)&gt; |
| [WorkspaceZones](-workspace-zones/index.md) | [jvm]<br>object [WorkspaceZones](-workspace-zones/index.md) : Table&lt;[WorkspaceZoneEntity](-workspace-zone-entity/index.md)&gt; |

## Properties

| Name | Summary |
|---|---|
| [utilities](utilities.md) | [jvm]<br>val Database.[utilities](utilities.md): EntitySequence&lt;[UtilityEntity](-utility-entity/index.md), [Utilities](-utilities/index.md)&gt; |
| [workspaces](workspaces.md) | [jvm]<br>val Database.[workspaces](workspaces.md): EntitySequence&lt;[WorkspaceEntity](-workspace-entity/index.md), [Workspaces](-workspaces/index.md)&gt; |
| [workspaceTags](workspace-tags.md) | [jvm]<br>val Database.[workspaceTags](workspace-tags.md): EntitySequence&lt;[WorkspaceTagEntity](-workspace-tag-entity/index.md), [WorkspaceTags](-workspace-tags/index.md)&gt; |
| [workspaceUtilities](workspace-utilities.md) | [jvm]<br>val Database.[workspaceUtilities](workspace-utilities.md): EntitySequence&lt;[WorkspaceUtilityEntity](-workspace-utility-entity/index.md), [WorkspaceUtilities](-workspace-utilities/index.md)&gt; |
| [workspaceZones](workspace-zones.md) | [jvm]<br>val Database.[workspaceZones](workspace-zones.md): EntitySequence&lt;[WorkspaceZoneEntity](-workspace-zone-entity/index.md), [WorkspaceZones](-workspace-zones/index.md)&gt; |
