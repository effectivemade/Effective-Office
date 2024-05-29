package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class EmbedX(
    val type: String,
    val url: String
)