package office.effective.features.auth.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface ApiKeyEntity : Entity<ApiKeyEntity> {
    companion object : Entity.Factory<ApiKeyEntity>()

    var id : UUID
    var keyValue : String
}

object ApiKeys : Table<ApiKeyEntity>("api_keys"){
    val id = uuid("id").bindTo { it.id }.primaryKey()
    val value = varchar("key_value").bindTo { it.keyValue }
}

val Database.apikeys get() = this.sequenceOf(ApiKeys)
