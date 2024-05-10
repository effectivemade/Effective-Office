package band.effective.feedback.domain.repository

interface Requester {
    suspend fun requestFeedback(requester: String, requested: String):Result<Unit>
}