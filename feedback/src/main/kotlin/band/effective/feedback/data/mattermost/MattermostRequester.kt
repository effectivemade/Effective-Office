package band.effective.feedback.data.mattermost

import band.effective.feedback.domain.repository.Requester
import band.effective.feedback.utils.getDirect

class MattermostRequester(private val mattermostApi: MattermostApi) : Requester {
    override suspend fun requestFeedback(requester: String, requested: String): Result<Unit> = runCatching {
        val bot = mattermostApi.me()
        val userRequested = mattermostApi.getUserInfo(userName = requested)
        val direct = mattermostApi.getDirect(id1 = bot.id, id2 = userRequested.id)
        mattermostApi.writeMessage(
            channelId = direct.id,
            message = "Привет! @${requester} запросил у тебя обратную связь. Напиши её пожалуйста и пометь хештегом. Спасибо."
        )
    }
}