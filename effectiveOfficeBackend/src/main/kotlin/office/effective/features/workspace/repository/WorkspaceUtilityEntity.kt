package office.effective.features.workspace.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.uuid

interface WorkspaceUtilityEntity: Entity<WorkspaceUtilityEntity> {
    companion object : Entity.Factory<WorkspaceUtilityEntity>()
    var utility: UtilityEntity
    var workspace: WorkspaceEntity
    var count: Int
}

object WorkspaceUtilities: Table<WorkspaceUtilityEntity>("workspace_utilities") {
    val utilityId = uuid("utility_id").primaryKey().references(Utilities) { it.utility }
    val workspaceId = uuid("workspace_id").primaryKey().references(Workspaces) { it.workspace }
    val count = int("count").bindTo { it.count }
}

val Database.workspaceUtilities get() = this.sequenceOf(WorkspaceUtilities)
