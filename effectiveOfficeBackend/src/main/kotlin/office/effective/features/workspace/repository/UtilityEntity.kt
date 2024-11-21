package office.effective.features.workspace.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface UtilityEntity: Entity<UtilityEntity> {
    companion object : Entity.Factory<UtilityEntity>()
    var id: UUID
    var name: String
    var iconUrl: String
}

object Utilities: Table<UtilityEntity>("utilities") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val iconUrl = varchar("icon_url").bindTo { it.iconUrl }
}

val Database.utilities get() = this.sequenceOf(Utilities)
