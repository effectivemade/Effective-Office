package office.effective.features.user.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.UUID

interface IntegrationEntity : Entity<IntegrationEntity> {
    companion object : Entity.Factory<IntegrationEntity>()

    var id: UUID?
    var name: String
    var iconUrl: String
}

object Integrations : Table<IntegrationEntity>("integrations") {
    val id = uuid("id").bindTo { it.id }.primaryKey()
    val name = varchar("name").bindTo { it.name }
    val iconUrl = varchar("icon_url").bindTo { it.iconUrl }
}

val Database.integrations get() = this.sequenceOf(Integrations)