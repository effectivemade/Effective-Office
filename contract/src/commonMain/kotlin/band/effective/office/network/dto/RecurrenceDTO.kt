package band.effective.office.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecurrenceDTO(
    val interval: Int? = null, // period bookings
    val freq: String, // DAILY, WEEKLY, MONTHLY, YEARLY
    val count: Int? = null,
    val until: Long? = null, // end date
    val byDay: List<Int> = listOf(),
    val byMonth: List<Int> = listOf(),
    val byYearDay: List<Int> = listOf(),
    val byHour: List<Int> = listOf()
)
