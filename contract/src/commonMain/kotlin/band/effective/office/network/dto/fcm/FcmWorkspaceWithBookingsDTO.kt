package band.effective.office.network.dto.fcm

import kotlinx.serialization.Serializable

@Serializable
data class FcmWorkspaceWithBookingsDTO(
    val id: String,
    val bookings: List<FcmEventDTO>
)
