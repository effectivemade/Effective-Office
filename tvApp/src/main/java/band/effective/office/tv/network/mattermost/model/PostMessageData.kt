package band.effective.office.tv.network.mattermost.model

import band.effective.office.tv.domain.model.message.BotMessage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostMessageData(
    @Json(name = "channel_id")
    val channelId: String,
    val message: String,
    @Json(name = "root_id")
    val rootId: String,
    val props: BotMessage? = null
)
