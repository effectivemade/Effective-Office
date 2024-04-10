package band.effective.office.tvServer.api.leader.models.eventInfo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Organizer(
    val email: String,
    val id: Int,
    val internationalPhone: Boolean?,
    val name: String,
    val phone: String,
    val photo: String?,
    @JsonNames( "photo_180")
    val photo180: String?,
    @JsonNames( "photo_360")
    val photo360: String?,
    @JsonNames( "photo_520")
    val photo520: String?,
    val position: String?,
    val userId: Int
)