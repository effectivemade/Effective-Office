//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.service](../index.md)/[WorkspaceService](index.md)

# WorkspaceService

[jvm]\
class [WorkspaceService](index.md)(workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) : [IWorkspaceService](../../office.effective.serviceapi/-i-workspace-service/index.md)

Class that implements the [IWorkspaceService](../../office.effective.serviceapi/-i-workspace-service/index.md) methods

## Constructors

| | |
|---|---|
| [WorkspaceService](-workspace-service.md) | [jvm]<br>constructor(workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [findAllByTag](find-all-by-tag.md) | [jvm]<br>open override fun [findAllByTag](find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag |
| [findAllFreeByPeriod](find-all-free-by-period.md) | [jvm]<br>open override fun [findAllFreeByPeriod](find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag which are free during the given period |
| [findAllZones](find-all-zones.md) | [jvm]<br>open override fun [findAllZones](find-all-zones.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)&gt;<br>Returns all workspace zones |
| [findById](find-by-id.md) | [jvm]<br>open override fun [findById](find-by-id.md)(id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Workspace](../../office.effective.model/-workspace/index.md)?<br>Retrieves a Workspace model by its id |
