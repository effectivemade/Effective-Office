package band.effective.office.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookingDTO(
    val owner: UserDTO,
    val participants: List<UserDTO>,
    val workspace: WorkspaceDTO,
    val id: String?,
    val beginBooking: Long,
    val endBooking: Long,
    val recurrence: RecurrenceDTO? = null
)
