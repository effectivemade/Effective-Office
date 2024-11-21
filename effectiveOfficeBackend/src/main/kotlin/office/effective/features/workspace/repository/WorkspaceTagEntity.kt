package office.effective.features.workspace.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface WorkspaceTagEntity: Entity<WorkspaceTagEntity> {
    companion object : Entity.Factory<WorkspaceTagEntity>()
    var id: UUID
    var name: String
}

object WorkspaceTags: Table<WorkspaceTagEntity>("workspace_tags") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
}

val Database.workspaceTags get() = this.sequenceOf(WorkspaceTags)
