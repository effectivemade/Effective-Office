package band.effective.office.tvServer.api.leader.models.eventInfo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Speaker(
    val completed: Boolean,
    @JsonNames("created_at")
    val createdAt: String,
    @JsonNames("event_id")
    val eventId: Int,
    val id: String,
    val moderation: String,
    val role: String,
    @JsonNames("role_id")
    val roleId: Int,
    val status: Int,
    val user: User,
    @JsonNames("user_id")
    val userId: Int,
    val visible: Boolean,
)