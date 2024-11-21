package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reaction(
    @Json(name = "channel_id")
    val channelId: String,
    @Json(name = "create_at")
    val createAt: Long,
    @Json(name = "delete_at")
    val deleteAt: Int,
    @Json(name = "emoji_name")
    val emojiName: String,
    @Json(name = "post_id")
    val postId: String,
    @Json(name = "remote_id")
    val remoteId: String,
    @Json(name = "update_at")
    val updateAt: Long,
    @Json(name = "user_id")
    val userId: String
)