package office.effective.features.user.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

interface UserIntegrationEntity : Entity<UserIntegrationEntity> {
    var userId: UserEntity
    var integrationId: IntegrationEntity
    val valueStr: String
}

object UsersIntegrations : Table<UserIntegrationEntity>("users_integrations") {
    val userId = uuid("user_id").references(Users) { it.userId }.primaryKey()
    val integrationId = uuid("integration_id").references(Integrations) { it.integrationId }.primaryKey()
    val valueStr = varchar("value").bindTo { it.valueStr }
}

val Database.usersinegrations get() = this.sequenceOf(UsersIntegrations)
