package band.effective.office.tv.network.leader.models.searchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaX(
    val currentPage: Int,
    val pageCount: Int,
    val perPage: Int,
    val totalCount: Int
)