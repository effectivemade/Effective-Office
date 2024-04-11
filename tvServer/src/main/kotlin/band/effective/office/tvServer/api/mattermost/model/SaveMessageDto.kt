package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class SaveMessageDto(
    val author: Author?,
    val channelId: String?,
    val directId: String?,
    val finish: String?,
    val from_bot: String?,
    val id: String?,
    val rootId: String?,
    val start: String?,
    val text: String?
)