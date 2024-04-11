package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostResponse(
    val channel_id: String,
    val create_at: Long,
    val delete_at: Long,
    val edit_at: Long,
    val id: String,
    val message: String,
    val original_id: String,
    val pending_post_id: String,
    val root_id: String,
    val type: String,
    val update_at: Long,
    val user_id: String
)