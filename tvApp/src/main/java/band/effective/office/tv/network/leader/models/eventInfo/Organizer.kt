package band.effective.office.tv.network.leader.models.eventInfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Organizer(
    val company: Any?,
    val companyId: Any?,
    val email: String,
    val id: Int,
    val internationalPhone: Boolean?,
    val name: String,
    val phone: String,
    val photo: String?,
    @Json(name = "photo_180")
    val photo180: String?,
    @Json(name = "photo_360")
    val photo360: String?,
    @Json(name = "photo_520")
    val photo520: String?,
    val photos: Any?,
    val position: String?,
    val telegram: Any?,
    val url: Any?,
    val userId: Int
)