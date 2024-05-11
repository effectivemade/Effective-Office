import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.data.mattermost.MattermostFeedbackRepository
import band.effective.feedback.data.mattermost.MattermostRequester
import band.effective.feedback.data.ydb.YdbFeedbackRepository
import band.effective.feedback.data.ydb.YdbFeedbackRequestRepository
import band.effective.feedback.domain.model.FeedbackRequest
import band.effective.feedback.domain.model.FeedbackStorage
import band.effective.feedback.utils.Env
import band.effective.feedback.utils.KtorClientBuilder
import band.effective.feedback.utils.createSessionRetryContext

suspend fun main() {
    val request = FeedbackRequest("maxim.mishchenko", "maxim.mishchenko", FeedbackStorage.Mattermost)

    val mattermostApi = MattermostApi(
        client = KtorClientBuilder { setToken(Env.mattermostBotKey) },
        baseUrl = Env.mattermostServer
    )
    val sessionRetryContext = createSessionRetryContext(
        authorizedKeyJson = Env.ydbKeyJson,
        connectionString = Env.ydbConnectionString
    )
    val requester = MattermostRequester(mattermostApi = mattermostApi)
    val mattermostFeedbackRepository = MattermostFeedbackRepository(mattermostApi)
    val ydbFeedbackRepository = YdbFeedbackRepository(
        sessionRetryContext = sessionRetryContext,
        mattermostApi = mattermostApi
    )
    val ydbFeedbackRequestRepository = YdbFeedbackRequestRepository(sessionRetryContext)
    //ydbFeedbackRepository.addFeedback(Feedback("maxim.mishchenko", "лучший"),"maxim.mishchenko").getOrThrow()
    ydbFeedbackRequestRepository.addRequest(request).getOrThrow()
    println(ydbFeedbackRequestRepository.getRequest(request.requester, request.requested).getOrThrow())
    ydbFeedbackRequestRepository.removeRequest(request).getOrThrow()
    println("hello world")
}