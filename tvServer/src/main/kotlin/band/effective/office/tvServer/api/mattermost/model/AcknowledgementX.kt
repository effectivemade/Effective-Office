package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class AcknowledgementX(
    val acknowledged_at: Long,
    val post_id: String,
    val user_id: String
)