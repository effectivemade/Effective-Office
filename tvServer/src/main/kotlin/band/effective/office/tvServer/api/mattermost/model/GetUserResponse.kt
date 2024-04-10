package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUserResponse(
    val auth_service: String,
    val create_at: Long,
    val delete_at: Long,
    val email: String,
    val first_name: String,
    val id: String,
    val last_name: String,
    val last_picture_update: Long,
    val locale: String,
    val nickname: String,
    val roles: String,
    val timezone: Timezone,
    val update_at: Long,
    val username: String
)