package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class Submission(
    val notion_url: String = "",
    val feedback: String = ""
)