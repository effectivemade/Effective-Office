package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class ReactionX(
    val create_at: Long,
    val emoji_name: String,
    val post_id: String,
    val user_id: String
)