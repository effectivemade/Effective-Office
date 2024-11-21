package office.effective.model

import office.effective.features.user.repository.UsersTagEntity
import java.util.*

data class UserModel(
    var fullName: String,
    var id: UUID?,
    var tag: UsersTagEntity?,
    var active: Boolean,
    var role: String?,
    var avatarURL: String?,
    var integrations: Set<IntegrationModel>?,
    var email: String
)