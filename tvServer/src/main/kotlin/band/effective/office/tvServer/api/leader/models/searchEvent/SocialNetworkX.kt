package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable

@Serializable
data class SocialNetworkX(
    val alias: String,
    val url: String
)