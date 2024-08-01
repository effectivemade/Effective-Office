package band.effective.office.tv.network.clockify.models.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClockifyRequest(
    val amountShown: String,
    val dateRangeEnd: String,
    val dateRangeStart: String,
    val detailedFilter: DetailedFilter,
    val exportType: String,
    val projects: Projects,
    val rounding: Boolean,
)