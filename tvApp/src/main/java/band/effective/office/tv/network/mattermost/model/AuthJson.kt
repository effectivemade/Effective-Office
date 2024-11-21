package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthJson(
    @Json(name = "auth_data")
    val authData: String,
    @Json(name = "auth_service")
    val authService: String,
    @Json(name = "bot_description")
    val botDescription: String,
    @Json(name = "create_at")
    val createAt: Long,
    @Json(name = "delete_at")
    val deleteAt: Int,
    @Json(name = "disable_welcome_email")
    val disableWelcomeEmail: Boolean,
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    val id: String,
    @Json(name = "is_bot")
    val isBot: Boolean,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "last_password_update")
    val lastPasswordUpdate: Long,
    @Json(name = "last_picture_update")
    val lastPictureUpdate: Long,
    val locale: String,
    val nickname: String,
    @Json(name = "notify_props")
    val notifyProps: NotifyProps,
    val position: String,
    val roles: String,
    val timezone: Timezone,
    @Json(name = "update_at")
    val updateAt: Long,
    val username: String
)