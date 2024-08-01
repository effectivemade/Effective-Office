package band.effective.office.tv.network.clockify.models.responce

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeEntry(
    @Json(name = "_id")
    val id: String,
    val approvalRequestId: Any?,
    val billable: Boolean,
    val clientId: String,
    val clientName: String,
    val currency: String,
    val description: String,
    val isLocked: Boolean,
    val projectColor: String,
    val projectId: String,
    val projectName: String,
    val tagIds: List<Any?>?,
    val taskId: String,
    val taskName: String,
    val timeInterval: TimeInterval,
    val type: String,
    val userEmail: String,
    val userId: String,
    val userName: String
)