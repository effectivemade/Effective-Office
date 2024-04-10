package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Emoji(
    val create_at: Long,
    val creator_id: String,
    val delete_at: Long,
    val id: String,
    val name: String,
    val update_at: Long
)