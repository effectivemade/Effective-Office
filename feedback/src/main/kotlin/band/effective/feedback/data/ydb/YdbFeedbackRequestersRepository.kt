package band.effective.feedback.data.ydb

import band.effective.feedback.domain.repository.FeedbackRequestersRepository
import band.effective.feedback.utils.getResultQuery
import tech.ydb.table.SessionRetryContext

class YdbFeedbackRequestersRepository(private val sessionRetryContext: SessionRetryContext) :
    FeedbackRequestersRepository {
    override fun getRequesters(requested: String): List<String> {
        return sessionRetryContext.getResultQuery("select requester from Requests where requested = \"${requested}\"") {
            getColumn("requester").text
        }
    }
}