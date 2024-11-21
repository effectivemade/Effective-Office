//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.repository](../index.md)/[WorkspaceRepository](index.md)/[addUtilityToWorkspace](add-utility-to-workspace.md)

# addUtilityToWorkspace

[jvm]\
fun [~~addUtilityToWorkspace~~](add-utility-to-workspace.md)(utilityId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html), count: [UInt](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-int/index.html))

---

### Deprecated

API does not involve adding utility to workspaces

---

Adds utility to workspace by their id. If the utility has already been added to the workspace, the count value will be overwritten

#### Author

Daniil Zavyalov

#### Parameters

jvm

| |
|---|
| utilityId |
| workspaceId |
| count | quantity of the given utility in the workspace |

#### Throws

| | |
|---|---|
| [InstanceNotFoundException](../../office.effective.common.exception/-instance-not-found-exception/index.md) | if workspace or utility with given id doesn't exist in the database |
