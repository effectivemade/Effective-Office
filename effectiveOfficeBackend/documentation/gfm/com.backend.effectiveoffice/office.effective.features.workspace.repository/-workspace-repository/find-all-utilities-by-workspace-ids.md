//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)/[findAllUtilitiesByWorkspaceIds](find-all-utilities-by-workspace-ids.md)

# findAllUtilitiesByWorkspaceIds

[jvm]\
fun [findAllUtilitiesByWorkspaceIds](find-all-utilities-by-workspace-ids.md)(ids: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)&gt;): [HashMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html)&lt;[UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Utility](../../office.effective.model/-utility/index.md)&gt;&gt;

Finds utilities for multiple workspaces in a single query.

To reduce the number of database queries, use this method when you need to get utilities for multiple workspaces.

#### Return

Returns a HashMap that maps user ids and lists with their integrations

#### Author

Daniil Zavyalov

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if user with the given id doesn't exist in the database |
