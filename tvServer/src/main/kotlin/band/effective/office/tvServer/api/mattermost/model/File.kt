package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class File(
    val create_at: Long,
    val delete_at: Long,
    val extension: String,
    val has_preview_image: Boolean,
    val height: Int,
    val id: String,
    val mime_type: String,
    val name: String,
    val post_id: String,
    val size: Int,
    val update_at: Long,
    val user_id: String,
    val width: Int
)