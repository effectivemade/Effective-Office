package band.effective.feedback.data.mattermost

import band.effective.feedback.domain.model.Feedback
import band.effective.feedback.domain.repository.FeedbackRepository
import band.effective.feedback.utils.getDirect

class MattermostFeedbackRepository(private val mattermostApi: MattermostApi) :
    FeedbackRepository {
    override suspend fun addFeedback(feedback: Feedback, requester: String): Result<Unit> = runCatching {
        val userRequester = mattermostApi.getUserInfo(userName = requester)
        val bot = mattermostApi.me()
        val channel = mattermostApi.getDirect(id1 = bot.id, id2 = userRequester.id)
        mattermostApi.writeMessage(channel.id, "@${feedback.name}: ${feedback.text}")
    }
}