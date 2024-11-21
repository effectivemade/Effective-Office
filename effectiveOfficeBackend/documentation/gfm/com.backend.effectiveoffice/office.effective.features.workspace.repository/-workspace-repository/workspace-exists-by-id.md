//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)/[workspaceExistsById](workspace-exists-by-id.md)

# workspaceExistsById

[jvm]\
fun [workspaceExistsById](workspace-exists-by-id.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Returns whether a workspace with the given id exists.

TODO: existence check temporary always returns true

#### Return

true if [Workspace](../../office.effective.model/-workspace/index.md) with the given [workspaceId](workspace-exists-by-id.md) exists in the database

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| workspaceId | id of requested workspace |
