package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "channel_display_name")
    val channelDisplayName: String,
    @Json(name = "channel_name")
    val channelName: String,
    @Json(name = "channel_type")
    val channelType: String,
    val post: Post,
    @Json(name = "sender_name")
    val senderName: String,
    @Json(name = "set_online")
    val setOnline: Boolean,
    @Json(name = "team_id")
    val teamId: String
)