package band.effective.feedback.domain.repository

import band.effective.feedback.domain.model.Feedback

interface FeedbackRepository {
    fun addFeedback(feedback: Feedback): Result<Unit>
}