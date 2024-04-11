package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class NotifyProps(
    val channel: String,
    val desktop: String,
    val desktop_sound: String,
    val email: String,
    val first_name: String,
    val mention_keys: String,
    val push: String
)