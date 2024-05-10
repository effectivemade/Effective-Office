package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(val channel_id: String, val message: String)