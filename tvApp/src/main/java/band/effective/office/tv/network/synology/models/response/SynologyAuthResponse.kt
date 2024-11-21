package band.effective.office.tv.network.synology.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SynologyAuthResponse(
    @Json(name = "success")
    val isSuccess: Boolean,
    val data: SynologyDataAuth?
)

@JsonClass(generateAdapter = true)
data class SynologyDataAuth(
    @Json(name = "did")
    val did: String?,
    @Json(name = "sid")
    val sid: String?,
)
