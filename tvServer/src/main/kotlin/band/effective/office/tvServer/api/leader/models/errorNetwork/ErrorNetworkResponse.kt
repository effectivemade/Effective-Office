package band.effective.office.tvServer.api.leader.models.errorNetwork

import kotlinx.serialization.Serializable

@Serializable
data class ErrorNetworkResponse(
    val code: Int?,
    val error: Error?,
    val errors: Errors?,
    val message: String?
)