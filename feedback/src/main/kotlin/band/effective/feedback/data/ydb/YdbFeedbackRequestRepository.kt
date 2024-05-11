package band.effective.feedback.data.ydb

import band.effective.feedback.domain.model.FeedbackRequest
import band.effective.feedback.domain.repository.FeedbackRequestRepository
import band.effective.feedback.utils.executeQuery
import band.effective.feedback.utils.getResultQuery
import band.effective.feedback.utils.newId
import kotlinx.serialization.json.Json
import tech.ydb.table.SessionRetryContext

class YdbFeedbackRequestRepository(private val sessionRetryContext: SessionRetryContext) : FeedbackRequestRepository {
    override fun addRequest(request: FeedbackRequest): Result<Unit> = runCatching {
        val id = sessionRetryContext.newId("Requests")
        val savePlace = Json.encodeToString(FeedbackStorageDescriptor, request.storage)
        sessionRetryContext.executeQuery("UPSERT INTO Requests (id, requester, requested, savePlace) VALUES ($id, \"${request.requester}\", \"${request.requested}\", cast(@@$savePlace@@ as json))")
    }

    override fun getRequest(requester: String, requested: String): Result<FeedbackRequest> = runCatching {
        sessionRetryContext.getResultQuery("select * from Requests where requester = \"$requester\" and requested = \"$requested\"") {
            FeedbackRequest(
                requester = getColumn("requester").text,
                requested = getColumn("requested").text,
                storage = Json.decodeFromString(FeedbackStorageDescriptor, getColumn("savePlace").json)
            )
        }.first()
    }

    override fun removeRequest(request: FeedbackRequest): Result<Unit> = runCatching {
        sessionRetryContext.executeQuery("delete from Requests where requester = \"${request.requester}\" and requested = \"${request.requested}\" and JSON_VALUE(savePlace, \"\$.type\") = \"${request.storage.type.name}\"")
    }
}