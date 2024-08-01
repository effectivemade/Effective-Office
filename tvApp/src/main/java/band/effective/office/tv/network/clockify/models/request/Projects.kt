package band.effective.office.tv.network.clockify.models.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Projects(
    val ids: List<String>
)