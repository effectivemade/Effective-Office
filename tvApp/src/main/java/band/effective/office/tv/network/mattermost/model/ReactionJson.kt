package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReactionJson(
    val broadcast: Broadcast,
    val data: DataX,
    val event: String,
    val seq: Int
)