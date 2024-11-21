package band.effective.office.tv.network.duolingo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DuolingoResponse(
    @Json(name = "users")
    val users: List<User>
)