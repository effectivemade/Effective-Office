package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable

@Serializable
data class MetaX(
    val currentPage: Int,
    val pageCount: Int,
    val perPage: Int,
    val totalCount: Int
)