package band.effective.office.tv.network.leader.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimezoneX(
    val minutes: Int,
    val value: String
)