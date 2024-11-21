//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)/[findAllFreeByPeriod](find-all-free-by-period.md)

# findAllFreeByPeriod

[jvm]\
fun [findAllFreeByPeriod](find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](../../office.effective.model/-workspace/index.md)&gt;

Returns all workspaces with the given tag which are free during the given period

#### Return

List of [Workspace](../../office.effective.model/-workspace/index.md) with the given [tag](find-all-free-by-period.md)

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
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if tag doesn't exist in the database |
