package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class CreatePostRequest(
    val channel_id: String,
    val file_ids: List<String>? = null,
    val message: String,
    val metadata: MetadataX? = null,
    val root_id: String? = null,
    @JsonNames("props")
    val props: SaveMessageDto? = null
)