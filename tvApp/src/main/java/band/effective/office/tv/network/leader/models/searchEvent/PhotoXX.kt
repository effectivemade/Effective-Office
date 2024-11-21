package band.effective.office.tv.network.leader.models.searchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoXX(
    val full: String,
    val thumb: ThumbXX
)