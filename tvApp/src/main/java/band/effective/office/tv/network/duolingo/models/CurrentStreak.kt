package band.effective.office.tv.network.duolingo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentStreak(
    val endDate: String,
    val length: Int,
    val startDate: String
)