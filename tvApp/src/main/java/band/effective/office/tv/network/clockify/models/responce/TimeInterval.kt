package band.effective.office.tv.network.clockify.models.responce

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeInterval(
    val duration: Int,
    val end: String,
    val start: String
)