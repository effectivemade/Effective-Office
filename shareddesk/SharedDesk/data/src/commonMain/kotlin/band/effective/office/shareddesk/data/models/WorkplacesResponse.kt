package band.effective.office.shareddesk.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class WorkplacesResponse(
    val list: List<WorkplaceDto>,
)