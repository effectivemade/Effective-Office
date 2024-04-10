package band.effective.office.tvServer.route.mattermost

import kotlinx.serialization.Serializable

@Serializable
data class WebHookDto(
    val channel_id: String,
    val channel_name: String,
    val file_ids: String,
    val post_id: String,
    val team_domain: String,
    val team_id: String,
    val text: String,
    val timestamp: Long,
    val token: String,
    val trigger_word: String,
    val user_id: String,
    val user_name: String
)