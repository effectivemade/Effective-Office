package band.effective.office.tvServer.api.leader.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class StatX(
    @JsonNames("active_participants")
    val activeParticipants: Int?,
    val moderationTime: String?,
    val monthEventCount: Int?,
    val ntiPercent: Double?,
    val participantAverage: String?,
    val participantCount: Int?,
    val regionScope: Int?,
    val uniqueUsers: Int?
)

