package band.effective.office.tv.network.clockify.models.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailedFilter(
    val sortColumn: String
)