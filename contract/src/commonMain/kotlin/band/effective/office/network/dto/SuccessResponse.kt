package band.effective.office.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(
    val status: String
)