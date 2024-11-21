//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.facade](../index.md)/[WorkspaceFacade](index.md)/[findById](find-by-id.md)

# findById

[jvm]\
fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)

Retrieves a [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) by its id

#### Return

[WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with the given [id](find-by-id.md)

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| id | id of requested workspace. Should be valid UUID |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if workspace with the given id doesn't exist in database |
