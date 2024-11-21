package office.effective.features.booking.repository

import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.Users
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.Workspaces
import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.jdbcTimestamp
import org.ktorm.schema.timestamp
import org.ktorm.schema.uuid
import java.sql.Timestamp
import java.time.Instant
import java.util.*

interface WorkspaceBookingEntity: Entity<WorkspaceBookingEntity> {
    companion object : Entity.Factory<WorkspaceBookingEntity>()
    var owner: UserEntity
    var workspace: WorkspaceEntity
    var id: UUID
    var beginBooking: Instant
    var endBooking: Instant
}

object WorkspaceBooking: Table<WorkspaceBookingEntity>("workspace_booking") {
    val ownerId = uuid("owner_id").references(Users) { it.owner }
    val workspaceId = uuid("workspace_id").references(Workspaces) { it.workspace }
    val id = uuid("id").primaryKey().bindTo { it.id }
    val beginBooking = timestamp("begin_booking").bindTo { it.beginBooking }
    val endBooking = timestamp("end_booking").bindTo { it.endBooking }
}

val Database.workspaceBooking get() = this.sequenceOf(WorkspaceBooking)
