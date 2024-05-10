package band.effective.feedback.domain.model

data class FeedbackRequest(
    val requester: String,
    val requested: String,
    val storage: FeedbackStorage
)
