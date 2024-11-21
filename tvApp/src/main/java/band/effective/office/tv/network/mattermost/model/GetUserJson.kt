package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetUserJson(
    @Json(name = "auth_service")
    val authService: String?,
    @Json(name = "create_at")
    val createAt: Long?,
    @Json(name = "delete_at")
    val deleteAt: Long?,
    val email: String?,
    @Json(name = "email_verified")
    val emailVerified: String?,
    @Json(name = "failed_attempts")
    val failedAttempts: Long?,
    @Json(name = "first_name")
    val firstName: String?,
    val id: String,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "last_password_update")
    val lastPasswordUpdate: Long?,
    @Json(name = "last_picture_update")
    val lastPictureUpdate: Long?,
    val locale: String?,
    @Json(name = "mfa_active")
    val mfaActive: String?,
    val nickname: String?,
    @Json(name = "notify_props")
    val notifyProps: NotifyPropsX?,
    val props: PropsX?,
    val roles: String?,
    @Json(name = "terms_of_service_create_at")
    val termsOfServiceCreateAt: Long?,
    @Json(name = "terms_of_service_id")
    val termsOfServiceId: String?,
    val timezone: TimezoneX?,
    @Json(name = "update_at")
    val updateAt: Long?,
    val username: String
)