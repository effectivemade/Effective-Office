package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class GetPostsFromChannelResponse(
    val next_post_id: String,
    val order: List<String>
)