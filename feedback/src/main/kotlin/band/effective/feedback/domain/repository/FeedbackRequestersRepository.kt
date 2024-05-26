package band.effective.feedback.domain.repository

interface FeedbackRequestersRepository {
    fun getRequesters(requested: String): List<String>
}