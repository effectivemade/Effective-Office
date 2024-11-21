package band.effective.office.tv.network.leader.models.searchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ParticipantsX(
    val count: Int,
    val list: List<ParticipantsItem>
)