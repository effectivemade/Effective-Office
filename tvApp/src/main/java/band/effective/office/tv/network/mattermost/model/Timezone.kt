package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Timezone(
    val automaticTimezone: String,
    val manualTimezone: String,
    val useAutomaticTimezone: String
)