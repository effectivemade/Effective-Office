package band.effective.office.tvServer.api.duolingo.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class DuolingoResponse(
    @JsonNames("users")
    val users: List<User>
)