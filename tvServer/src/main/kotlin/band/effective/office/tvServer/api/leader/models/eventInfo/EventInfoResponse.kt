package band.effective.office.tvServer.api.leader.models.eventInfo

import kotlinx.serialization.Serializable

@Serializable
data class EventInfoResponse(
    val data: Data
)