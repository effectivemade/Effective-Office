package band.effective.office.tvServer.api.leader.models.searchEvent

import band.effective.office.tv.network.leader.models.ThemeX
import band.effective.office.tvServer.api.leader.models.HallX
import band.effective.office.tvServer.api.leader.models.TimezoneX
import band.effective.office.tvServer.api.leader.models.TypeX
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Item(
    val city: String?,
    @JsonNames("city_id")
    val cityId: Int?,
    val createdBy: Int,
    @JsonNames("date_end")
    val dateEnd: String,
    @JsonNames("date_start")
    val dateStart: String,
    val delivered: Boolean,
    val finished: Boolean,
    val format: String,
    @JsonNames("full_info")
    val fullInfo: String,
    @JsonNames("full_name")
    val fullName: String,
    val halls: List<HallX>,
    val id: Int,
    val indexedAt: Int,
    val isFavorite: Boolean,
    @JsonNames("live_public")
    val livePublic: Boolean,
    val moderation: String,
    val needFeedback: Boolean,
    val needStartNotification: Boolean,
    @JsonNames("participation_format")
    val participationFormat: String,
    val photo: String?,
    @JsonNames("photo_180")
    val photo180: String?,
    @JsonNames("photo_360")
    val photo360: String?,
    @JsonNames("photo_520")
    val photo520: String?,
    val stat: StatXX,
    val status: String,
    val themes: List<ThemeX>,
    val timezone: TimezoneX?,
    val type: TypeX
)