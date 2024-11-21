package office.effective.features.workspace.repository

import org.ktorm.database.Database
import org.ktorm.dsl.isNull
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.UUID

interface WorkspaceEntity: Entity<WorkspaceEntity> {
    companion object : Entity.Factory<WorkspaceEntity>()
    var id: UUID
    var name: String
    var tag: WorkspaceTagEntity
    var zone: WorkspaceZoneEntity?
}

object Workspaces: Table<WorkspaceEntity>("workspaces") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val tagId = uuid("tag_id").references(WorkspaceTags) { it.tag }
    val zoneId = uuid("zone_id").references(WorkspaceZones) { it.zone }
}

val Database.workspaces get() = this.sequenceOf(Workspaces)
