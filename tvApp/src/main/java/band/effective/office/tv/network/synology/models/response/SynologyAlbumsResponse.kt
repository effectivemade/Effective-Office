package band.effective.office.tv.network.synology.models.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SynologyAlbumsResponse(
    @Json(name = "data")
    val albumsData: AlbumsData,
    @Json(name = "success")
    val success: Boolean
)

@JsonClass(generateAdapter = true)
data class AlbumsData(
    @Json(name = "list")
    val albums: List<Albums>
)

@JsonClass(generateAdapter = true)
data class Albums (
    @Json(name = "cant_migrate_condition", ignore = true)
    val cantMigrateCondition: CantMigrateCondition = CantMigrateCondition(),
    @Json(name = "condition", ignore = true)
    val condition: Condition = Condition(),
    @Json(name = "create_time")
    val createTime: Int,
    @Json(name = "end_time")
    val endTime: Int,
    @Json(name = "freeze_album")
    val freezeAlbum: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "item_count")
    val item_count: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "owner_user_id")
    val owner_user_id: Int,
    @Json(name = "passphrase")
    val passphrase: String,
    @Json(name = "shared")
    val shared: Boolean,
    @Json(name = "sort_by")
    val sortBy: String,
    @Json(name = "sort_direction")
    val sortDirection: String,
    @Json(name = "start_time")
    val startTime: Int,
    @Json(name = "temporary_shared")
    val temporaryShared: Boolean,
    @Json(name = "type")
    val type: String,
    @Json(name = "version")
    val version: Int
)


class CantMigrateCondition
class Condition