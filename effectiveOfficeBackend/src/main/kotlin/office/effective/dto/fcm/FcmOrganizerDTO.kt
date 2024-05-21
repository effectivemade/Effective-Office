package office.effective.dto.fcm

import kotlinx.serialization.Serializable

@Serializable
data class FcmOrganizerDTO(
    val id: String,
    val fullName: String,
    val email: String
)
