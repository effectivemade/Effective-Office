package band.effective.office.tvServer.model

import kotlinx.serialization.Serializable

@Serializable
data class MattermostUser(
    val id: String,
    val name: String
)
