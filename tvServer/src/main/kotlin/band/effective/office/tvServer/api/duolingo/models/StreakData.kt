package band.effective.office.tvServer.api.duolingo.models

import kotlinx.serialization.Serializable

@Serializable
data class StreakData(
    val currentStreak: CurrentStreak?
)