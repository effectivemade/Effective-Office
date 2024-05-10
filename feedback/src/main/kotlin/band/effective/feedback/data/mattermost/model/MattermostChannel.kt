package band.effective.feedback.data.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class MattermostChannel(
    val create_at: Long,
    val creator_id: String,
    val delete_at: Int,
    val display_name: String,
    val extra_update_at: Int,
    val header: String,
    val id: String,
    val last_post_at: Long,
    val last_root_post_at: Long,
    val name: String,
    val purpose: String,
    val shared: Boolean,
    val team_id: String,
    val total_msg_count: Int,
    val total_msg_count_root: Int,
    val type: String,
    val update_at: Long
)