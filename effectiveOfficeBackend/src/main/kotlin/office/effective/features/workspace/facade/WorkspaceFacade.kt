package office.effective.features.workspace.facade

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.ValidationException
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.features.workspace.service.WorkspaceService
import office.effective.model.Workspace
import java.lang.IllegalArgumentException
import java.util.UUID

class WorkspaceFacade(private val service: WorkspaceService, private val converter: WorkspaceFacadeConverter) {

    fun findById(id: String): WorkspaceDTO {
        val uuid: UUID
        try {
            uuid = UUID.fromString(id)
        } catch (ex: IllegalArgumentException) {
            throw ValidationException("Provided id is not UUID: " + ex.message)
        }
        val workspace: Workspace = service.findById(uuid)
            ?: throw InstanceNotFoundException(Workspace::class, "Workspace with id $id not found", uuid)
        return converter.workspaceModelToDto(workspace)
    }

    fun findAllByTag(tag: String): List<WorkspaceDTO> {
        return service.findAllByTag(tag).map {
            converter.workspaceModelToDto(it)
        }
    }
}