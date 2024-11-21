package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotifyPropsX(
    val channel: String,
    val desktop: String,
    @Json(name = "desktop_sound")
    val desktopSound: String,
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "mention_keys")
    val mentionKeys: String,
    val push: String
)