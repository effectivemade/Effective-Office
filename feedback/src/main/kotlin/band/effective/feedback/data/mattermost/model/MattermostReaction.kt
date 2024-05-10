package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class MattermostReaction(
    val channel_id: String,
    val create_at: Long,
    val delete_at: Int,
    val emoji_name: String,
    val post_id: String,
    val remote_id: String,
    val update_at: Long,
    val user_id: String
)