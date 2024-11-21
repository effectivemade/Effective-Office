//[com.backend.effectiveoffice](../../../index.md)/[office.effective.serviceapi](../index.md)/[IWorkspaceService](index.md)

# IWorkspaceService

interface [IWorkspaceService](index.md)

Interface that provides methods for manipulating [Workspace](../../office.effective.model/-workspace/index.md) objects.

#### Inheritors

| |
|---|
| [WorkspaceService](../../office.effective.features.workspace.service/-workspace-service/index.md) |

## Functions

| Name | Summary |
|---|---|
| [findAllByTag](find-all-by-tag.md) | [jvm]<br>abstract fun [findAllByTag](find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag |
| [findAllFreeByPeriod](find-all-free-by-period.md) | [jvm]<br>abstract fun [findAllFreeByPeriod](find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag which are free during the given period |
| [findAllZones](find-all-zones.md) | [jvm]<br>abstract fun [findAllZones](find-all-zones.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceZone](../../office.effective.model/-workspace-zone/index.md)&gt;<br>Returns all workspace zones |
| [findById](find-by-id.md) | [jvm]<br>abstract fun [findById](find-by-id.md)(id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Workspace](../../office.effective.model/-workspace/index.md)?<br>Retrieves a Workspace model by its id |
