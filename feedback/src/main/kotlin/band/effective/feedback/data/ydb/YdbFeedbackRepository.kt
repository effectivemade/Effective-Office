package band.effective.feedback.data.ydb

import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.domain.model.Feedback
import band.effective.feedback.domain.repository.FeedbackRepository
import band.effective.feedback.utils.executeQuery
import band.effective.feedback.utils.newId
import tech.ydb.table.SessionRetryContext

class YdbFeedbackRepository(
    private val sessionRetryContext: SessionRetryContext,
    private val mattermostApi: MattermostApi
) : FeedbackRepository {
    override suspend fun addFeedback(feedback: Feedback, requester: String): Result<Unit> = runCatching {
        val id = sessionRetryContext.newId("feedback")
        val name = mattermostApi.getUserInfo(feedback.name).run { "$first_name $last_name" }
        sessionRetryContext.executeQuery("UPSERT INTO feedback (id, name, text) VALUES ($id, \"$name\", \"${feedback.text}\")")
    }
}