package office.effective.dto

import kotlinx.serialization.Serializable

@Serializable
@Deprecated(
    message = "Deprecated since 1.0 api version",
    replaceWith = ReplaceWith(
        expression = "WorkspaceDTO",
        imports = ["office.effective.dto.WorkspaceDTO"]
    )
)
data class WorkspaceResponseDTO(
    val id: String,
    val name: String,
    val utilities: List<UtilityDTO>,
    val zone: WorkspaceZoneDTO? = null,
    val tag: String
)
