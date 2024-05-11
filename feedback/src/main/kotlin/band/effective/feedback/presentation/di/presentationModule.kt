package band.effective.feedback.presentation.di

import band.effective.feedback.data.mattermost.MattermostFeedbackRepository
import band.effective.feedback.domain.useCase.SaveFeedbackUseCase
import band.effective.feedback.domain.useCase.StartFeedbackLoopUseCase
import org.koin.dsl.module

fun presentationModule() = module {
    factory {
        SaveFeedbackUseCase(
            feedbackRequestRepository = get(),
            feedbackRepositoryMap = get(),
            defaultFeedbackRepository = get<MattermostFeedbackRepository>()
        )
    }
    factory {
        StartFeedbackLoopUseCase(
            feedbackRequestRepository = get(),
            requester = get()
        )
    }
}