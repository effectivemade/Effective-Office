package band.effective.office.tvServer.api.duolingo.models

import kotlinx.serialization.Serializable

@Serializable
data class LiveOpsFeature(
    val endTimestamp: Int,
    val startTimestamp: Int,
    val type: String
)