package band.effective.office.tv.network.leader.models.eventInfo

import band.effective.office.tv.network.leader.models.AddressX
import band.effective.office.tv.network.leader.models.HallX
import band.effective.office.tv.network.leader.models.SpaceX
import band.effective.office.tv.network.leader.models.StatX
import band.effective.office.tv.network.leader.models.ThemeX
import band.effective.office.tv.network.leader.models.TimezoneX
import band.effective.office.tv.network.leader.models.TypeX
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val access: String,
    val address: AddressX,
    val afterQuizId: Any?,
    val certificate: Boolean,
    val certificate_text: Any?,
    val city: String,
    @Json(name = "city_id")
    val cityId: Int,
    val createdBy: Int,
    @Json(name = "date_end")
    val dateEnd: String,
    @Json(name = "date_start")
    val dateStart: String,
    val documents: List<Any?>,
    val ecpRequired: Boolean?,
    val expectedParticipantCount: Any?,
    val finished: Boolean,
    val format: String,
    @Json(name = "full_info")
    val fullInfo: String,
    @Json(name = "full_name")
    val fullName: String,
    val halls: List<HallX>,
    @Json(name = "hash_tags")
    val hashTags: List<Any?>,
    val hosts: List<Any?>,
    val id: Int,
    val info: Any?,
    @Json(name = "is_competition")
    val isCompetition: Boolean,
    val live: Any?,
    @Json(name = "live_public")
    val livePublic: Boolean,
    val media: Any?,
    val moderation: String,
    val needFeedback: Boolean,
    val needStartNotification: Boolean,
    val networkProposals: Any?,
    @Json(name = "network_parent_id")
    val networkParentId: Any?,
    val networking: Any?,
    val organizers: List<Organizer>,
    @Json(name = "participation_format")
    val participationFormat: String,
    val partners: List<Any?>,
    val photo: String?,
    @Json(name = "photo_180")
    val photo180: String?,
    @Json(name = "photo_360")
    val photo360: String?,
    @Json(name = "photo_520")
    val photo520: String?,
    val place: Any?,
    val quizId: Any?,
    @Json(name = "registration_date_end")
    val registrationDateEnd: String,
    @Json(name = "registration_date_start")
    val registrationDateStart: String,
    val schedules: List<Any?>,
    val similar: List<Similar>,
    val space: SpaceX,
    val speakers: List<Speaker>,
    val stat: StatX,
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