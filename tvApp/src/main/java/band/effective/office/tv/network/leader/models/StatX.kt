package band.effective.office.tv.network.leader.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatX(
    @Json(name = "active_participants")
    val activeParticipants: Int?,
    val moderationTime: String?,
    val monthEventCount: Int?,
    val ntiPercent: Double?,
    val participantAverage: String?,
    val participantCount: Int?,
    val regionScope: Int?,
    val uniqueUsers: Int?
)

