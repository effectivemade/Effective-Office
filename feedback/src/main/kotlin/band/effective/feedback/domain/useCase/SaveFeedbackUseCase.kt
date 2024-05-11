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
    suspend fun save(feedback: Feedback, requester: String) = runCatching {
        feedbackRequestRepository.getRequest(requester, feedback.name).fold(
            onSuccess = { request ->
                val storage = request.storage
                val feedbackRepository = feedbackRepositoryMap.getOrDefault(storage.type, defaultFeedbackRepository)
                feedbackRepository.addFeedback(feedback, requester).getOrElse {
                    defaultFeedbackRepository.addFeedback(feedback, requester).getOrThrow()
                }
                feedbackRequestRepository.removeRequest(request).getOrThrow()
            },
            onFailure = {
                defaultFeedbackRepository.addFeedback(feedback, requester).getOrThrow()
            })
    }
}