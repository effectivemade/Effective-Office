package band.effective.office.tvServer.route.mattermost

import band.effective.office.tvServer.model.MattermostMessage
import kotlinx.serialization.Serializable

@Serializable
data class MattermostMessageDto (
    val message: MattermostMessage,
    val start: String?,
    val finish: String?
)