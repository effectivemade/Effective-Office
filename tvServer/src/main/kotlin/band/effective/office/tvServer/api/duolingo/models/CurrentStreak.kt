package band.effective.office.tvServer.api.duolingo.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrentStreak(
    val endDate: String,
    val length: Int,
    val startDate: String
)