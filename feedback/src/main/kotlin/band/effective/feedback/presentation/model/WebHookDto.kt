package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class WebHookDto(
    val text: String,
    val user_name: String,
    val post_id: String = "",
)