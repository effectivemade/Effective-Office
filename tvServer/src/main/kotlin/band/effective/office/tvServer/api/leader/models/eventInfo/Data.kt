package band.effective.office.tvServer.api.leader.models.eventInfo

import band.effective.office.tv.network.leader.models.ThemeX
import band.effective.office.tvServer.api.leader.models.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Data(
    val access: String,
    val address: AddressX,
    val certificate: Boolean,
    val city: String,
    @JsonNames("city_id")
    val cityId: Int,
    val createdBy: Int,
    @JsonNames( "date_end")
    val dateEnd: String,
    @JsonNames( "date_start")
    val dateStart: String,
    val ecpRequired: Boolean?,
    val finished: Boolean,
    val format: String,
    @JsonNames( "full_info")
    val fullInfo: String,
    @JsonNames( "full_name")
    val fullName: String,
    val halls: List<HallX>,
    val id: Int,
    @JsonNames("is_competition")
    val isCompetition: Boolean,
    @JsonNames("live_public")
    val livePublic: Boolean,
    val moderation: String,
    val needFeedback: Boolean,
    val needStartNotification: Boolean,
    val organizers: List<Organizer>,
    @JsonNames("participation_format")
    val participationFormat: String,
    val photo: String?,
    @JsonNames("photo_180")
    val photo180: String?,
    @JsonNames("photo_360")
    val photo360: String?,
    @JsonNames("photo_520")
    val photo520: String?,
    @JsonNames("registration_date_end")
    val registrationDateEnd: String,
    @JsonNames("registration_date_start")
    val registrationDateStart: String,
    val similar: List<Similar>,
    val space: SpaceX,
    val speakers: List<Speaker>,
    val status: String,
    val themes: List<ThemeX>,
    val timezone: TimezoneX?,
    val type: TypeX
)