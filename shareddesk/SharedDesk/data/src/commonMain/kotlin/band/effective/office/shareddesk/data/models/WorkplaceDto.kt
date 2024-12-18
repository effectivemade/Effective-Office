package band.effective.office.shareddesk.data.models

import kotlinx.serialization.Serializable

@Serializable
data class WorkplaceDto(
    val id: String,
    val isBusy: Boolean,
)
