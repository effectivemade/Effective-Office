package band.effective.feedback.domain.repository

import band.effective.feedback.domain.model.FeedbackRequest

interface FeedbackRequestRepository {
    fun addRequest(request: FeedbackRequest): Result<Unit>
    fun getRequest(requester: String, requested: String): Result<FeedbackRequest>
    fun removeRequest(request: FeedbackRequest): Result<Unit>
}