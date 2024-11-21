//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.facade](../index.md)/[WorkspaceFacade](index.md)/[findAllFreeByPeriod](find-all-free-by-period.md)

# findAllFreeByPeriod

[jvm]\
fun [findAllFreeByPeriod](find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), endTimestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)&gt;

Returns all [Workspace](../../office.effective.model/-workspace/index.md)s with the given tag which are free during the given period

#### Return

List of [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with the given [tag](find-all-free-by-period.md)

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| tag | tag name of requested workspaces |
| beginTimestamp | period start time |
| endTimestamp | period end time |

#### Throws

| | |
|---|---|
| [ValidationException](../../office.effective.common.exception/-validation-exception/index.md) | if begin or end timestamp less than 0, greater than max timestamp or if end timestamp less than or equal to begin timestamp |
