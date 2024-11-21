package office.effective.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String,         // UUIDv4
    val fullName: String,
    val active: Boolean,
    val role: String,
    val avatarUrl: String,
    val integrations: List<IntegrationDTO>?,
    val email: String,
    val tag: String,
)
