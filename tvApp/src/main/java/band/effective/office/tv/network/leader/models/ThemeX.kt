package band.effective.office.tv.network.leader.models

import band.effective.office.tv.network.leader.models.searchEvent.PhotoXXX
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThemeX(
    val childCount: Int,
    val code: Any?,
    val createdBy: Int,
    val gid: Int,
    val id: Int,
    val keywords: List<String>,
    val moderatedAt: Any?,
    val moderatedBy: Any?,
    val name: String,
    @Json(name = "old_type")
    val oldType: String?,
    val parentId: Int?,
    val photo: PhotoXXX,
    val photos: Any?,
    val priority: Int,
    val status: Int,
    @Json(name = "updated_at")
    val updatedAt: String?,
    val visible: Boolean
)