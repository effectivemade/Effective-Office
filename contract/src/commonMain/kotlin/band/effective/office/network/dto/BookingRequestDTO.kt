package band.effective.office.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookingRequestDTO (
    val ownerEmail: String?,
    val participantEmails: List<String>,
    val workspaceId: String,
    val beginBooking: Long,
    val endBooking: Long,
    val recurrence: RecurrenceDTO? = null
)
