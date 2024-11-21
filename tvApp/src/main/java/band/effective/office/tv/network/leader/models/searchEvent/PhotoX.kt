package band.effective.office.tv.network.leader.models.searchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoX(
    val full: String,
    val thumb: ThumbX
)