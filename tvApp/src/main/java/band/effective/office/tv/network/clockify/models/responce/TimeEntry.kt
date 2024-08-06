package band.effective.office.tv.network.clockify.models.responce

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeEntry(
    val timeInterval: TimeInterval,
    val userEmail: String,
    val userName: String
)