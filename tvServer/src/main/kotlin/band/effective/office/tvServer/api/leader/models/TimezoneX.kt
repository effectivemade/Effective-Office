package band.effective.office.tvServer.api.leader.models

import kotlinx.serialization.Serializable

@Serializable
data class TimezoneX(
    val minutes: Int,
    val value: String
)