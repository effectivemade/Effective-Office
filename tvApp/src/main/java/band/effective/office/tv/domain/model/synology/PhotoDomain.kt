package band.effective.office.tv.domain.model.synology

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.network.synology.models.response.PhotoInfo
import band.effective.office.tv.network.synology.models.response.SynologyListResponse
import band.effective.office.tv.network.synology.models.response.SynologyPhotoSAlbumsResponse
import java.net.URLEncoder

data class PhotoDomain(
    val photoThumb: String
)

fun SynologyListResponse.toDomain(sid: String) =
    data.files.map {
        PhotoDomain(
            photoThumb = formThumbPhoto(it.path, sid)
        )
    }

fun SynologyPhotoSAlbumsResponse.toDomain(sid: String) =
    photoData.photosInfo.map {photoInfo ->
        val size: String = when {
            photoInfo.additional.thumbnail.sizeXl == "ready" -> "xl"
            photoInfo.additional.thumbnail.sizeM == "ready" -> "m"
            photoInfo.additional.thumbnail.sizeSm == "ready" -> "sm"
            else -> "sm"
        }
        PhotoDomain(
            photoThumb = formThumbPhotoFromAlbum(
                id = photoInfo.id,
                cacheKey = photoInfo.additional.thumbnail.cacheKey,
                sid = sid,
                size = size
            )
        )
    }

fun List<PhotoInfo>.toDomain(sid: String) =
    map {photoInfo ->
        val size: String = when {
            photoInfo.additional.thumbnail.sizeXl == "ready" -> "xl"
            photoInfo.additional.thumbnail.sizeM == "ready" -> "m"
            photoInfo.additional.thumbnail.sizeSm == "ready" -> "sm"
            else -> "sm"
        }
        PhotoDomain(
            photoThumb = formThumbPhotoFromAlbum(
                id = photoInfo.id,
                cacheKey = photoInfo.additional.thumbnail.cacheKey,
                sid = sid,
                size = size
            )
        )
    }
fun formThumbPhoto(photoPath: String, sid: String): String {
    val encodePhotoPath = URLEncoder.encode(photoPath, "utf-8")
    return "${BuildConfig.apiSynologyUrl}/webapi/entry.cgi?api=SYNO.FileStation.Thumb&version=2&_sid=${sid}&method=get&path=${encodePhotoPath}"
}

fun formThumbPhotoFromAlbum(cacheKey: String, id: Int, sid: String, size: String): String =
    "${BuildConfig.apiSynologyUrl}/webapi/entry.cgi/?cache_key=${cacheKey}&id=${id-6}&api=SYNO.Foto.Thumbnail&method=get&version=1&type=unit&size=${size}&_sid=${sid}"