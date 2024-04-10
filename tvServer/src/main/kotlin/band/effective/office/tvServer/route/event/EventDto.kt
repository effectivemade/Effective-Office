package band.effective.office.tvServer.route.event

import band.effective.office.tvServer.model.Event
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val name: String,
    val photo: String,
    val monthsInCompany: Int?,
    val yearsInCompany: Int?,
    val type: EventType
) {
    companion object {
        fun Event.toDto(): EventDto = when (this) {
            is Event.AnnualAnniversary -> EventDto(
                name = employeeName,
                photo = employeePhotoUrl,
                monthsInCompany = null,
                yearsInCompany = yearsInCompany,
                type = EventType.AnnualAnniversary
            )

            is Event.Birthday -> EventDto(
                name = name,
                photo = photoUrl,
                monthsInCompany = null,
                yearsInCompany = null,
                type = EventType.Birthday
            )

            is Event.MonthAnniversary -> EventDto(
                name = employeeName,
                photo = employeePhotoUrl,
                monthsInCompany = monthsInCompany,
                yearsInCompany = null,
                type = EventType.MonthAnniversary
            )

            is Event.NewEmployee -> EventDto(
                name = employeeName,
                photo = employeePhotoUrl,
                monthsInCompany = null,
                yearsInCompany = null,
                type = EventType.NewEmployee
            )
        }
    }
}
