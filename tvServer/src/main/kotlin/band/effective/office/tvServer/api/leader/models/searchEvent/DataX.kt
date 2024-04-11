package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class DataX(
    @JsonNames( "_items")
    val items: List<Item>,
    @JsonNames( "_meta")
    val meta: MetaX?
)