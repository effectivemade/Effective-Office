package office.effective.dto

import kotlinx.serialization.Serializable
import model.RecurrenceDTO

@Serializable
data class BookingResponseDTO (
    val owner: UserDTO,
    val participants: List<UserDTO>,
    val workspace: WorkspaceDTO,
    val id: String,
    val beginBooking: Long,
    val endBooking: Long,
    val recurrence: RecurrenceDTO? = null,
    val recurringBookingId: String? = null
)