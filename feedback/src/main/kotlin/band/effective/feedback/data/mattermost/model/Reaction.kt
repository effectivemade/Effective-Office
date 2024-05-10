package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    val user_id: String,
    val post_id: String,
    val emoji_name: String
)
