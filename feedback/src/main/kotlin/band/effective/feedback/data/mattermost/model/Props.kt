package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Props(
    val last_search_pointer: String
)