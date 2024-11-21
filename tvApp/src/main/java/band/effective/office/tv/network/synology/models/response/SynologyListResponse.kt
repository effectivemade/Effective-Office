package band.effective.office.tv.network.synology.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SynologyListResponse (
    @Json(name = "data")
    val data: DataList,
    @Json(name = "success")
    val isSuccess: Boolean,
)

@JsonClass(generateAdapter = true)
data class DataList (
    @Json(name = "files")
    val files: List<FilesSynology>,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "total")
    val total: Int
)

@JsonClass(generateAdapter = true)
data class FilesSynology(
    @Json(name = "isdir")
    val isDir: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "path")
    val path: String
)