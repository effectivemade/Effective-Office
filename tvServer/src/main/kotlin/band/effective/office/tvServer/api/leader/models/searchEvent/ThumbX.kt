package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ThumbX(
    @JsonNames("360")
    val size360: String,
    @JsonNames("520")
    val size520: String
)