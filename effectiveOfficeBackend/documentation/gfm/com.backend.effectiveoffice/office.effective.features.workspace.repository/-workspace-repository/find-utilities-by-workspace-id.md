//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)/[findUtilitiesByWorkspaceId](find-utilities-by-workspace-id.md)

# findUtilitiesByWorkspaceId

[jvm]\
fun [findUtilitiesByWorkspaceId](find-utilities-by-workspace-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Utility](../../office.effective.model/-utility/index.md)&gt;

Returns all workspace utilities by workspace id.

#### Return

List of [Utility](../../office.effective.model/-utility/index.md) for [Workspace](../../office.effective.model/-workspace/index.md) with the given id

#### Author

Daniil Zavyalov

#### Parameters

jvm

| |
|---|
| workspaceId |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if workspace with given id doesn't exist in the database |
