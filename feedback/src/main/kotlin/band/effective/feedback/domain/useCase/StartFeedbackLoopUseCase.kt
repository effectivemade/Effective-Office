package band.effective.feedback.domain.useCase

import band.effective.feedback.domain.model.FeedbackRequest
import band.effective.feedback.domain.model.FeedbackStorage
import band.effective.feedback.domain.repository.FeedbackRequestRepository
import band.effective.feedback.domain.repository.Requester

class StartFeedbackLoopUseCase(
    private val feedbackRequestRepository: FeedbackRequestRepository,
    private val requester: Requester
) {
    suspend fun start(
        userRequester: String,
        usersRequested: List<String>,
        feedbackStorage: FeedbackStorage = FeedbackStorage.Ydb
    ) = runCatching {
        fun String.getRequest() = FeedbackRequest(
            requester = userRequester,
            requested = this,
            storage = feedbackStorage
        )
        usersRequested.forEach { userRequested ->
            requester.requestFeedback(requester = userRequester, requested = userRequested).getOrThrow()
            feedbackRequestRepository.addRequest(request = userRequested.getRequest()).getOrThrow()
        }
    }
}