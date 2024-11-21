package band.effective.office.tvServer.api.leader.models.errorNetwork

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val code: Int,
    val message: String,
    val name: String,
    val status: Int
)