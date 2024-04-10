package band.effective.office.tvServer.api.leader.models.eventInfo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Similar(
    @JsonNames( "date_end")
    val dateEnd: String,
    @JsonNames( "date_start")
    val dateStart: String,
    @JsonNames( "full_name")
    val fullName: String,
    val id: Int,
    val photo: String?
)