package band.effective.feedback.domain.repository

import band.effective.feedback.domain.model.Feedback

interface FeedbackRepository {
    suspend fun addFeedback(feedback: Feedback, requester: String): Result<Unit>
}