package band.effective.office.network.dto.fcm

import kotlinx.serialization.Serializable

@Serializable
data class FcmEventDTO(
    val id: String?,
    val startTime: Long,
    val endTime: Long,
    val organizer: FcmOrganizerDTO?
)
