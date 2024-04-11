package band.effective.office.tvServer.route.mattermost

import band.effective.office.tvServer.model.MattermostMessage
import band.effective.office.tvServer.model.MattermostUser
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val text: String,
    val author: String
) {
    fun toMessage(): MattermostMessage =
        MattermostMessage(
            channelId = "", author = MattermostUser(id = "", name = author), id = "", text = text, rootId = ""
        )
}
