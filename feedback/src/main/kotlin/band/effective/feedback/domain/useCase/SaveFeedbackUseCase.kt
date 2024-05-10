package band.effective.feedback.domain.useCase

import band.effective.feedback.domain.model.Feedback
import band.effective.feedback.domain.model.FeedbackStorageType
import band.effective.feedback.domain.repository.FeedbackRepository
import band.effective.feedback.domain.repository.FeedbackRequestRepository

class SaveFeedbackUseCase(
    private val feedbackRequestRepository: FeedbackRequestRepository,
    private val feedbackRepositoryMap: Map<FeedbackStorageType, FeedbackRepository>,
    private val defaultFeedbackRepository: FeedbackRepository
) {
    fun save(feedback: Feedback, requester: String) = runCatching {
        val request = feedbackRequestRepository.getRequest(requester, feedback.name).getOrThrow()
        val storage = request.storage
        val feedbackRepository = feedbackRepositoryMap.getOrDefault(storage.type, defaultFeedbackRepository)
        try {
            feedbackRepository.addFeedback(feedback)
        } catch (e: Throwable) {
            defaultFeedbackRepository.addFeedback(feedback)
        }
        feedbackRequestRepository.removeRequest(request).getOrThrow()
    }
}