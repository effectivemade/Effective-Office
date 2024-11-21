package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostMessageJson(
    val action: String,
    val seq: Int,
    val data: PostMessageData
) {
}