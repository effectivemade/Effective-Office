package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class SendData(
    val requester: String,
    val requested: String
)
