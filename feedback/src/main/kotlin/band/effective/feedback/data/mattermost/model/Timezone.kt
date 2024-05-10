package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Timezone(
    val automaticTimezone: String,
    val manualTimezone: String,
    val useAutomaticTimezone: String
)