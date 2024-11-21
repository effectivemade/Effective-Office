//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)

# WorkspaceRepository

[jvm]\
class [WorkspaceRepository](index.md)(database: Database, converter: [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md))

Class that executes database queries with workspaces

All WorkspaceRepository methods return workspaces with workspace zone and all utilities

## Constructors

| | |
|---|---|
| [WorkspaceRepository](-workspace-repository.md) | [jvm]<br>constructor(database: Database, converter: [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addUtilityToWorkspace](add-utility-to-workspace.md) | [jvm]<br>fun [~~addUtilityToWorkspace~~](add-utility-to-workspace.md)(utilityId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), count: [UInt](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-int/index.html))<br>Adds utility to workspace by their id. If the utility has already been added to the workspace, the count value will be overwritten |
| [deleteById](delete-by-id.md) | [jvm]<br>fun [~~deleteById~~](delete-by-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html))<br>Deletes the workspace with the given id |
| [findAllByTag](find-all-by-tag.md) | [jvm]<br>fun [findAllByTag](find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag |
| [findAllFreeByPeriod](find-all-free-by-period.md) | [jvm]<br>fun [findAllFreeByPeriod](find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag which are free during the given period |
| [findAllUtilitiesByWorkspaceIds](find-all-utilities-by-workspace-ids.md) | [jvm]<br>fun [findAllUtilitiesByWorkspaceIds](find-all-utilities-by-workspace-ids.md)(ids: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)&gt;): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Utility](../../office.effective.model/-utility/index.md)&gt;&gt;<br>Finds utilities for multiple workspaces in a single query. |
| [findAllZones](find-all-zones.md) | [jvm]<br>fun [findAllZones](find-all-zones.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)&gt;<br>Returns all workspace zones |
| [findById](find-by-id.md) | [jvm]<br>fun [findById](find-by-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Workspace](../../office.effective.model/-workspace/index.md)?<br>Retrieves a workspace model by its id |
| [findUtilitiesByWorkspaceId](find-utilities-by-workspace-id.md) | [jvm]<br>fun [findUtilitiesByWorkspaceId](find-utilities-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Utility](../../office.effective.model/-utility/index.md)&gt;<br>Returns all workspace utilities by workspace id. |
| [save](save.md) | [jvm]<br>fun [~~save~~](save.md)(workspace: [Workspace](../../office.effective.model/-workspace/index.md)): [Workspace](../../office.effective.model/-workspace/index.md)<br>Saves a given workspace. If given model will have id, it will be ignored. Use the returned model for further operations |
| [utilityExistsById](utility-exists-by-id.md) | [jvm]<br>fun [utilityExistsById](utility-exists-by-id.md)(utilityId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a utility with the given id exists |
| [workspaceExistsById](workspace-exists-by-id.md) | [jvm]<br>fun [workspaceExistsById](workspace-exists-by-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether a workspace with the given id exists. |
