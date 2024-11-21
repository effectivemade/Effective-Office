package band.effective.office.tv.domain.model.message

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: String = "",
    val name: String = ""
)
