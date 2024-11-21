package band.effective.office.tv.network.leader.models.eventInfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Similar(
    val city: Any?,
    @Json(name = "date_end")
    val dateEnd: String,
    @Json(name = "date_start")
    val dateStart: String,
    @Json(name = "full_name")
    val fullName: String,
    val id: Int,
    val photo: String?
)