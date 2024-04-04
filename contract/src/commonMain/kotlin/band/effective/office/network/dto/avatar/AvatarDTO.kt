package band.effective.office.network.dto.avatar

import kotlinx.serialization.Serializable

@Serializable
data class AvatarDTO(
    val name: String = "",
    val image: String = "",
    val valid: Boolean = false,
    val city: String = "",
    val country: String = "",
    val success: Boolean = false,
    val rawData: String = "",
    val source: List<String> = emptyList()
)
