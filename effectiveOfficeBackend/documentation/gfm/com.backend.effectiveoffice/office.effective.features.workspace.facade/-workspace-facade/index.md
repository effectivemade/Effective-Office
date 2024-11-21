//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.workspace.facade](../index.md)/[WorkspaceFacade](index.md)

# WorkspaceFacade

[jvm]\
class [WorkspaceFacade](index.md)(service: [IWorkspaceService](../../office.effective.serviceapi/-i-workspace-service/index.md), converter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md), transactionManager: [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md))

Class used in routes to handle workspaces requests. Provides business transaction, data conversion and validation.

In case of an error, the database transaction will be rolled back.

## Constructors

| | |
|---|---|
| [WorkspaceFacade](-workspace-facade.md) | [jvm]<br>constructor(service: [IWorkspaceService](../../office.effective.serviceapi/-i-workspace-service/index.md), converter: [WorkspaceFacadeConverter](../../office.effective.features.workspace.converters/-workspace-facade-converter/index.md), transactionManager: [DatabaseTransactionManager](../../office.effective.common.utils/-database-transaction-manager/index.md), uuidValidator: [UuidValidator](../../office.effective.common.utils/-uuid-validator/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [findAllByTag](find-all-by-tag.md) | [jvm]<br>fun [findAllByTag](find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)&gt;<br>Returns all [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) with the given tag |
| [findAllFreeByPeriod](find-all-free-by-period.md) | [jvm]<br>fun [findAllFreeByPeriod](find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), endTimestamp: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)&gt;<br>Returns all [Workspace](../../office.effective.model/-workspace/index.md)s with the given tag which are free during the given period |
| [findAllZones](find-all-zones.md) | [jvm]<br>fun [findAllZones](find-all-zones.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceZoneDTO](../../office.effective.dto/-workspace-zone-d-t-o/index.md)&gt;<br>Returns all workspace zones |
| [findById](find-by-id.md) | [jvm]<br>fun [findById](find-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md)<br>Retrieves a [WorkspaceDTO](../../office.effective.dto/-workspace-d-t-o/index.md) by its id |
