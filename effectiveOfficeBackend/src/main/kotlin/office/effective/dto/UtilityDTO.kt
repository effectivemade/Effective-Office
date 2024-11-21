package office.effective.dto

import kotlinx.serialization.Serializable

@Serializable
data class UtilityDTO(val id: String, val name: String, val iconUrl: String, val count: Int)
