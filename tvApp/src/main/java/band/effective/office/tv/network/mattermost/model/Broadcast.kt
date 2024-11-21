package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Broadcast(
    @Json(name = "channel_id")
    val channelId: String,
    @Json(name = "connection_id")
    val connectionId: String,
    @Json(name = "omit_connection_id")
    val omitConnectionId: String,
    @Json(name = "omit_users")
    val omitUsers: Any?,
    @Json(name = "team_id")
    val teamId: String,
    @Json(name = "user_id")
    val userId: String
)