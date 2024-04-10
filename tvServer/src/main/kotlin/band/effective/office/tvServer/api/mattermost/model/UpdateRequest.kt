package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class UpdateRequest(
    val id: String,
    val message: String? = null,
    @JsonNames("props")
    val props: SaveMessageDto? = null
)