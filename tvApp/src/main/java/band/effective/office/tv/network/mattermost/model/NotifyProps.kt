package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotifyProps(
    val channel: String,
    val comments: String,
    val desktop: String,
    @Json(name = "desktop_sound")
    val desktopSound: String,
    @Json(name = "desktop_threads")
    val desktopThreads: String,
    val email: String,
    @Json(name = "email_threads")
    val emailThreads: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "mention_keys")
    val mentionKeys: String,
    val push: String,
    @Json(name = "push_status")
    val pushStatus: String,
    @Json(name = "push_threads")
    val pushThreads: String
)