package band.effective.feedback.presentation.di

import band.effective.feedback.data.mattermost.MattermostFeedbackRepository
import band.effective.feedback.data.mattermost.MattermostRequester
import band.effective.feedback.data.ydb.YdbFeedbackRepository
import band.effective.feedback.data.ydb.YdbFeedbackRequestRepository
import band.effective.feedback.domain.model.FeedbackStorageType
import band.effective.feedback.domain.repository.FeedbackRepository
import band.effective.feedback.domain.repository.FeedbackRequestRepository
import band.effective.feedback.domain.repository.Requester
import org.koin.dsl.module

fun domainModule() = module {
    factory<Requester> { MattermostRequester(mattermostApi = get()) }
    factory<FeedbackRequestRepository> { YdbFeedbackRequestRepository(sessionRetryContext = get()) }
    factory<MattermostFeedbackRepository> { MattermostFeedbackRepository(mattermostApi = get()) }
    factory<YdbFeedbackRepository> {
        YdbFeedbackRepository(
            sessionRetryContext = get(),
            mattermostApi = get()
        )
    }
    factory<Map<FeedbackStorageType, FeedbackRepository>> {
        mapOf(
            FeedbackStorageType.MATTERMOST to get<MattermostFeedbackRepository>(),
            FeedbackStorageType.YDB to get<YdbFeedbackRepository>()
        )
    }
}