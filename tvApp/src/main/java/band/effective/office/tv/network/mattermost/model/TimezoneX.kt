package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimezoneX(
    val automaticTimezone: String,
    val manualTimezone: String,
    val useAutomaticTimezone: String
)