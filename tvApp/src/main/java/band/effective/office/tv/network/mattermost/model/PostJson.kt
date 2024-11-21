package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostJson(
    val broadcast: Broadcast,
    val data: Data,
    val event: String,
    val seq: Int
)