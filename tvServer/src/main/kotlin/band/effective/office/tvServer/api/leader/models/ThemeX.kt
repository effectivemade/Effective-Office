package band.effective.office.tv.network.leader.models

import band.effective.office.tvServer.api.leader.models.searchEvent.PhotoXXX
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ThemeX(
    val childCount: Int,
    val createdBy: Int,
    val gid: Int,
    val id: Int,
    val keywords: List<String>,
    val name: String,
    @JsonNames("old_type")
    val oldType: String?,
    val parentId: Int?,
    val photo: PhotoXXX,
    val priority: Int,
    val status: Int,
    @JsonNames("updated_at")
    val updatedAt: String?,
    val visible: Boolean
)