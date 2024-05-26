package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class InterectiveMessageSubmit(
    val callback_id: String,
    val cancelled: Boolean,
    val channel_id: String,
    val state: String,
    val submission: Submission,
    val team_id: String,
    val type: String,
    val user_id: String
)