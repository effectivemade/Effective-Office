package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OtherJson(val seq: Int) {
}