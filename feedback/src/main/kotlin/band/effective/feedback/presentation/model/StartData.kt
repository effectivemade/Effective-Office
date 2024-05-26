package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class StartData(
    val requester: String,
    val requested: List<String>
)
