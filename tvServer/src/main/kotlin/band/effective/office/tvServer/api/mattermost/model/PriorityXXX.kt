package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class PriorityXXX(
    val priority: String,
    val requested_ack: Boolean
)