package band.effective.office.tvServer.model

import kotlinx.serialization.Serializable

@Serializable
data class MattermostMessage(
    val channelId: String,
    val author: MattermostUser,
    val id: String,
    val text: String,
    val rootId: String
)