package office.effective.features.workspace.service

import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import office.effective.serviceapi.IWorkspaceService
import java.time.Instant
import java.util.UUID

/**
 * Class that implements the [IWorkspaceService] methods
 */
class WorkspaceService(private val repository: WorkspaceRepository): IWorkspaceService {

    /**
     * Retrieves a Workspace model by its id
     *
     * @param id id of requested workspace
     * @return [Workspace] with the given [id] or null if workspace with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    override fun findById(id: UUID): Workspace? {
        return repository.findById(id)
    }

    /**
     * Returns all workspaces with the given tag
     *
     * @param tag tag name of requested workspaces
     * @return List of [Workspace] with the given [tag]
     * @author Daniil Zavyalov
     */
    override fun findAllByTag(tag: String): List<Workspace> {
        return repository.findAllByTag(tag)
    }

    /**
     * Returns all workspaces with the given tag which are free during the given period
     *
     * @param tag tag name of requested workspaces
     * @param beginTimestamp period start time
     * @param endTimestamp period end time
     * @return List of [Workspace] with the given [tag]
     * @author Daniil Zavyalov
     */
    override fun findAllFreeByPeriod(tag: String, beginTimestamp: Instant, endTimestamp: Instant): List<Workspace> {
        return repository.findAllFreeByPeriod(tag, beginTimestamp, endTimestamp)
    }

    /**
     * Returns all workspace zones
     *
     * @return List of all [WorkspaceZone]
     * @author Daniil Zavyalov
     */
    override fun findAllZones(): List<WorkspaceZone> {
        return repository.findAllZones()
    }
}