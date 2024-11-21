package band.effective.office.tv.network.leader.models.searchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipantsItem(
    val id: Int,
    val name: String,
    val photo: Any?
)