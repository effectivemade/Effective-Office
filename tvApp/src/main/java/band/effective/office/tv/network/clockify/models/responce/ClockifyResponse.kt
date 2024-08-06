package band.effective.office.tv.network.clockify.models.responce

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ClockifyResponse(
    @Json(name = "timeentries")
    val timeEntries: List<TimeEntry>,
)