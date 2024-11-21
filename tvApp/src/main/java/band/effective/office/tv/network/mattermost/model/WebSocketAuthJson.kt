package band.effective.office.tv.network.mattermost.model

import band.effective.office.tv.BuildConfig
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebSocketAuthJson(
    val action: String,
    val data: DataXX,
    val seq: Int
) {
    companion object {
        val default = WebSocketAuthJson(
            action = "authentication_challenge",
            seq = 1,
            data = DataXX(
                token = BuildConfig.mattermostBotToken
            )
        )
    }
}