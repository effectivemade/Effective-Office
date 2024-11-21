package office.effective.features.calendar.repository

import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.Workspaces
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface CalendarIdEntity : Entity<CalendarIdEntity> {
    companion object : Entity.Factory<CalendarIdEntity>()

    var calendarId: String
    var workspace: WorkspaceEntity
}

object CalendarIds : Table<CalendarIdEntity>("calendar_ids") {
    val calendarId = varchar("calendar_id").bindTo { it.calendarId } // must be unique
    val workspaceId = uuid("workspace_id").references(Workspaces) { it.workspace }.primaryKey()
}

val Database.calendarIds get() = this.sequenceOf(CalendarIds)