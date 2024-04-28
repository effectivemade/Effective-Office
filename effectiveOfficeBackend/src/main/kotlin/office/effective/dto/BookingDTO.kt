package office.effective.dto

import kotlinx.serialization.Serializable
import model.RecurrenceDTO

@Serializable
@Deprecated(
    message = "Deprecated since 1.0 api version",
    replaceWith = ReplaceWith(
        expression = "BookingRequestDTO or BookingResponseDTO",
        imports = ["office.effective.dto.BookingRequestDTO"]
    )
)
data class BookingDTO (
    val owner: UserDTO,
    val participants: List<UserDTO>,
    val workspace: WorkspaceDTO,
    val id: String?,
    val beginBooking: Long,
    val endBooking: Long,
    val recurrence: RecurrenceDTO? = null
)
