//[com.backend.effectiveoffice](../../../index.md)/[office.effective.features.calendar.repository](../index.md)/[CalendarIdsRepository](index.md)

# CalendarIdsRepository

[jvm]\
class [CalendarIdsRepository](index.md)(db: Database, converter: [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md))

Class that executes database queries with workspace calendar ids

## Constructors

| | |
|---|---|
| [CalendarIdsRepository](-calendar-ids-repository.md) | [jvm]<br>constructor(db: Database, converter: [WorkspaceRepositoryConverter](../../office.effective.features.workspace.converters/-workspace-repository-converter/index.md), workspaceRepository: [WorkspaceRepository](../../office.effective.features.workspace.repository/-workspace-repository/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [findAllCalendarsId](find-all-calendars-id.md) | [jvm]<br>fun [findAllCalendarsId](find-all-calendars-id.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Finds all Google calendar ids |
| [findByWorkspace](find-by-workspace.md) | [jvm]<br>fun [findByWorkspace](find-by-workspace.md)(workspaceId: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [findWorkspaceById](find-workspace-by-id.md) | [jvm]<br>fun [findWorkspaceById](find-workspace-by-id.md)(calendarId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Workspace](../../office.effective.model/-workspace/index.md) |
