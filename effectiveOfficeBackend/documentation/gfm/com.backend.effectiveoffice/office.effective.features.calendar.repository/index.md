//[com.backend.effectiveoffice](../../index.md)/[office.effective.features.calendar.repository](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CalendarIdEntity](-calendar-id-entity/index.md) | [jvm]<br>interface [CalendarIdEntity](-calendar-id-entity/index.md) : Entity&lt;[CalendarIdEntity](-calendar-id-entity/index.md)&gt; |
| [CalendarIds](-calendar-ids/index.md) | [jvm]<br>object [CalendarIds](-calendar-ids/index.md) : Table&lt;[CalendarIdEntity](-calendar-id-entity/index.md)&gt; |
| [CalendarIdsRepository](-calendar-ids-repository/index.md) | [jvm]<br>class [CalendarIdsRepository](-calendar-ids-repository/index.md)(db: Database, converter: [WorkspaceRepositoryConverter](../office.effective.features.workspace.converters/-workspace-repository-converter/index.md), workspaceRepository: [WorkspaceRepository](../office.effective.features.workspace.repository/-workspace-repository/index.md))<br>Class that executes database queries with workspace calendar ids |

## Properties

| Name | Summary |
|---|---|
| [calendarIds](calendar-ids.md) | [jvm]<br>val Database.[calendarIds](calendar-ids.md): EntitySequence&lt;[CalendarIdEntity](-calendar-id-entity/index.md), [CalendarIds](-calendar-ids/index.md)&gt; |
