package band.effective.office.tv.network.synology.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SynologyPhotoSAlbumsResponse(
    @Json(name = "data")
    val photoData: PhotoData,
    @Json(name = "success")
    val success: Boolean
)

@JsonClass(generateAdapter = true)
data class PhotoData(
    @Json(name = "list")
    val photosInfo: List<PhotoInfo>
)

@JsonClass(generateAdapter = true)
data class PhotoInfo(
    @Json(name = "additional")
    val additional: Additional,
    @Json(name = "filename")
    val filename: String,
    @Json(name = "filesize")
    val fileSize: Int,
    @Json(name = "folder_id")
    val folderId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "indexed_time")
    val indexedTime: Long,
    @Json(name = "owner_user_id")
    val ownerUserId: Int,
    @Json(name = "time")
    val time: Int,
    @Json(name = "type")
    val type: String
)
@JsonClass(generateAdapter = true)
data class Additional(
    @Json(name = "orientation")
    val orientation: Int,
    @Json(name = "orientation_original")
    val orientationOriginal: Int,
    @Json(name = "provider_user_id")
    val providerUserId: Int,
    @Json(name = "resolution")
    val resolution: Resolution,
    @Json(name = "thumbnail")
    val thumbnail: Thumbnail
)

@JsonClass(generateAdapter = true)
data class Resolution(
    @Json(name = "height")
    val height: Int,
    @Json(name = "width")
    val width: Int
)

@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "cache_key")
    val cacheKey: String,
    @Json(name = "m")
    val sizeM: String,
    @Json(name = "preview")
    val preview: String,
    @Json(name = "sm")
    val sizeSm: String,
    @Json(name = "unit_id")
    val unitId: Int,
    @Json(name = "xl")
    val sizeXl: String
)