package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class MattermostMessage(
    val channel_id: String,
    val create_at: Long,
    val delete_at: Int,
    val edit_at: Int,
    val hashtags: String,
    val id: String,
    val is_pinned: Boolean,
    val last_reply_at: Int,
    val message: String,
    val original_id: String,
    val pending_post_id: String,
    val props: PropsX,
    val reply_count: Int,
    val root_id: String,
    val type: String,
    val update_at: Long,
    val user_id: String
)