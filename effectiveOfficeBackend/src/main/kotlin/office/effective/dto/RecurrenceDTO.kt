package model

import kotlinx.serialization.Serializable

@Serializable
data class RecurrenceDTO(
    val interval: Int? = null,
    val freq: String,
    val count: Int? = null,
    val until: Long? = null,
    val byDay: List<Int> = listOf(),
    val byMonth: List<Int> = listOf(),
    val byYearDay: List<Int> = listOf(),
    val byHour: List<Int> = listOf()
)