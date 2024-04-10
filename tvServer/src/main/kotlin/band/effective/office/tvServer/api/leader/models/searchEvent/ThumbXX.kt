package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ThumbXX(
    @JsonNames("180")
    val size180: String,
    @JsonNames("360")
    val size360: String,
    @JsonNames("520")
    val size520: String
)