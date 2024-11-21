package band.effective.office.tv.network.leader.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeX(
    val id: Int,
    val name: String
)