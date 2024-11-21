package band.effective.office.tv.network.leader.models.eventInfo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val email: Any?,
    val employment: Any?,
    val employments: List<Any?>,
    @Json(name = "father_name")
    val fatherName: String?,
    @Json(name = "first_name")
    val firstName: String,
    val id: Int,
    @Json(name = "last_name")
    val lastName: String,
    val phone: Any?,
    val photo: Any?
)