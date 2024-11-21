//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)/[findAllByTag](find-all-by-tag.md)

# findAllByTag

[jvm]\
fun [findAllByTag](find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;

Returns all workspaces with the given tag

#### Return

List of [Workspace](../../office.effective.model/-workspace/index.md) with the given [tag](find-all-by-tag.md)

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| tag | tag name of requested workspaces |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if tag doesn't exist in the database |
