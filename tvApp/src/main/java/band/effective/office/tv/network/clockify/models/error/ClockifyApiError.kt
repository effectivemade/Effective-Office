package band.effective.office.tv.network.clockify.models.error

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClockifyApiError(
    val code: Int,
    val message: String
)