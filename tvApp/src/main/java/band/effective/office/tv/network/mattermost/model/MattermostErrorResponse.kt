package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MattermostErrorResponse(
    val detailed_error: String,
    val id: String,
    val message: String,
    val request_id: String,
    val status_code: Int
)