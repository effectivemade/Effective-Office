package band.effective.office.tv.network.synology.models.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SynologyApiError(
    @Json(name = "error")
    val error: SynologyError,
    @Json(name = "success")
    val isSuccess: Boolean
)

@JsonClass(generateAdapter = true)
data class SynologyError(
    @Json(name = "code")
    val code: Int
)
