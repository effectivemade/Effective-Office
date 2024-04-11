package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsX(
    val count: Int,
    val list: List<ParticipantsItem>
)