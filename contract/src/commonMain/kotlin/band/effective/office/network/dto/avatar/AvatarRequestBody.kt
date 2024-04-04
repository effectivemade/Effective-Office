package band.effective.office.network.dto.avatar

import kotlinx.serialization.Serializable

@Serializable
data class AvatarRequestBody(
    val username: String = "",
    val password: String = "",
    val email: String = ""
)
