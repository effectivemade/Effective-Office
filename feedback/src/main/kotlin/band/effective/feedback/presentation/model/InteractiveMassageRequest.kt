package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class InteractiveMassageRequest(
    val channel_id: String,
    val channel_name: String,
    val data_source: String,
    val post_id: String,
    val team_domain: String,
    val team_id: String,
    val trigger_id: String,
    val type: String,
    val user_id: String,
    val user_name: String,
    val context:Context
)