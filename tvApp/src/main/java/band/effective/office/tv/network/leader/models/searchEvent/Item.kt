package band.effective.office.tv.network.leader.models.searchEvent

import band.effective.office.tv.network.leader.models.HallX
import band.effective.office.tv.network.leader.models.SpaceX
import band.effective.office.tv.network.leader.models.ThemeX
import band.effective.office.tv.network.leader.models.TimezoneX
import band.effective.office.tv.network.leader.models.TypeX
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    val afterQuizId: Any?,
    val city: String,
    @Json(name = "city_id")
    val cityId: Int,
    val createdBy: Int,
    @Json(name = "date_end")
    val dateEnd: String,
    @Json(name = "date_start")
    val dateStart: String,
    val delivered: Boolean,
    val finished: Boolean,
    val format: String,
    @Json(name = "full_info")
    val fullInfo: String,
    @Json(name = "full_name")
    val fullName: String,
    val halls: List<HallX>,
    @Json(name = "hash_tags")
    val hashTags: List<Any>,
    val id: Int,
    val indexedAt: Int,
    val info: Any?,
    val isFavorite: Boolean,
    val live: Any?,
    @Json(name = "live_public")
    val livePublic: Boolean,
    val moderation: String,
    val needFeedback: Boolean,
    val needStartNotification: Boolean,
    @Json(name = "network_parent_id")
    val networkParentId: Any?,
    val networking: Any?,
    @Json(name = "participation_format")
    val participationFormat: String,
    val photo: String?,
    @Json(name = "photo_180")
    val photo180: String?,
    @Json(name = "photo_360")
    val photo360: String?,
    @Json(name = "photo_520")
    val photo520: String?,
    val place: Any?,
    val schedules: List<Any>,
    val space: SpaceX,
    val stat: StatXX,
    val status: String,
    @Json(name = "team_size_max")
    val teamSizeMax: Any?,
    @Json(name = "team_size_min")
    val teamSizeMin: Any?,
    @Json(name = "team_type")
    val teamType: Any?,
    val themes: List<ThemeX>,
    val timezone: TimezoneX?,
    val type: TypeX
)