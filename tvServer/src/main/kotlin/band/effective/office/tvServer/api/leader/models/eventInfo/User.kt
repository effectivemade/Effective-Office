package band.effective.office.tvServer.api.leader.models.eventInfo

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class User(
    @JsonNames( "father_name")
    val fatherName: String?,
    @JsonNames( "first_name")
    val firstName: String,
    val id: Int,
    @JsonNames( "last_name")
    val lastName: String,
)