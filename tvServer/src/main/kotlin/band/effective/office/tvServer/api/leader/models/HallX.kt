package band.effective.office.tvServer.api.leader.models

import band.effective.office.tvServer.api.leader.models.searchEvent.PhotoX
import kotlinx.serialization.Serializable

@Serializable
data class HallX(
    val capacity: Int?,
    val id: Int?,
    val name: String?,
    val photos: List<PhotoX>,
    val preparePeriod: Int?,
    val square: String?,
    val tags: List<String>?,
    val type: String?
)