package office.effective.features.workspace.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface WorkspaceZoneEntity: Entity<WorkspaceZoneEntity> {
    companion object : Entity.Factory<WorkspaceZoneEntity>()
    var id: UUID
    var name: String
}

object WorkspaceZones: Table<WorkspaceZoneEntity>("workspace_zones") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
}

val Database.workspaceZones get() = this.sequenceOf(WorkspaceZones)
