package office.effective.dto

import kotlinx.serialization.Serializable
import model.RecurrenceDTO

@Serializable
data class BookingRequestDTO (
    val ownerEmail: String?,
    val participantEmails: List<String>,
    val workspaceId: String,
    val beginBooking: Long,
    val endBooking: Long,
    val recurrence: RecurrenceDTO? = null
)
