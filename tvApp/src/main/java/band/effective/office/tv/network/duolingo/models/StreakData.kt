package band.effective.office.tv.network.duolingo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StreakData(
    val currentStreak: CurrentStreak?
)