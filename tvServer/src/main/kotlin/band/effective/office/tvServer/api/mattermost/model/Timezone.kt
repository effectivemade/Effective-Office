package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Timezone(
    val automaticTimezone: String,
    val manualTimezone: String
)