package band.effective.office.tv.network.leader.models.searchEvent

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThumbXX(
    @Json(name = "180")
    val size180: String,
    @Json(name = "360")
    val size360: String,
    @Json(name = "520")
    val size520: String
)