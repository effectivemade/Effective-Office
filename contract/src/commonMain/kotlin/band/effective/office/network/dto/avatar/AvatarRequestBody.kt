package band.effective.office.network.dto.avatar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvatarRequestBody(
    @SerialName ("username") val username: String = "",
    @SerialName ("password") val password: String = "",
    @SerialName ("email") val email: String = ""
) {
    override fun toString(): String {
        return """
        {
        "username" : "$username",
        "password" : "$password",
        "email" : "$email"
        }
    """.trimIndent()
    }
}
