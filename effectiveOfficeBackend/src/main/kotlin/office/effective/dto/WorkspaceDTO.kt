package office.effective.dto

import kotlinx.serialization.Serializable

@Serializable
data class WorkspaceDTO(
    val id: String,
    val name: String,
    val utilities: List<UtilityDTO>,
    val zone: WorkspaceZoneDTO? = null,
    val tag: String
)
