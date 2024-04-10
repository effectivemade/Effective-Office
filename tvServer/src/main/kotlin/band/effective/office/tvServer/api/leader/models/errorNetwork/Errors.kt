package band.effective.office.tvServer.api.leader.models.errorNetwork

import kotlinx.serialization.Serializable

@Serializable
data class Errors(
    val paginationSize: List<String>
)