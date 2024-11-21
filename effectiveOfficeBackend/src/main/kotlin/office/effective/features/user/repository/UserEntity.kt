package office.effective.features.user.repository

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.util.*

interface UserEntity : Entity<UserEntity> {
    companion object : Entity.Factory<UserEntity>()

    var id: UUID
    var fullName: String
    var tag: UsersTagEntity
    var active: Boolean
    var role: String?
    var avatarURL: String?
    var email: String
}

object Users : Table<UserEntity>("users") {
    val id = uuid("id").bindTo { it.id }.primaryKey()
    val fullName = varchar("full_name").bindTo { it.fullName }
    val tagId = uuid("tag_id").references(UsersTags) { it.tag }//ManyToOne
    val active = boolean("active").bindTo { it.active }
    val role = varchar("role").bindTo { it.role }
    val avatarURL = varchar("avatar_url").bindTo { it.avatarURL }
    val email = varchar("email").bindTo { it.email }
}

val Database.users get() = this.sequenceOf(Users)