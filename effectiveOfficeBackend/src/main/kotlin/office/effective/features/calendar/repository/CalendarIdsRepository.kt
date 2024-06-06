package office.effective.features.calendar.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Workspace
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.ktorm.entity.toList
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * Class that executes database queries with workspace calendar ids
 */
class CalendarIdsRepository(
    private val db: Database,
    private val converter: WorkspaceRepositoryConverter,
    private val workspaceRepository: WorkspaceRepository,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * @return String - id of calendar for specified workspace
     * */
    fun findByWorkspace(workspaceId: UUID): String {
        logger.debug("[findByWorkspace] retrieving a calendar id for workspace with id={}", workspaceId.toString())
        return db.calendarIds.find { it.workspaceId eq workspaceId }?.calendarId ?: throw InstanceNotFoundException(
            CalendarIdEntity::class, "Cannot found calendar id to workspace id $workspaceId", null
        )
    }

    /**
     * @param calendarId
     * @param shouldFindUtilities whether workspace utilities should be found or not
     * @return [Workspace] model by calendar id (String)
     * */
    fun findWorkspaceById(calendarId: String, shouldFindUtilities: Boolean = true): Workspace {
        logger.debug("[findWorkspaceById] retrieving a workspace with calendar id={}", calendarId)
        try {
            val workspaceEntity =
                db.calendarIds.find { it.calendarId eq calendarId }?.workspace ?: throw InstanceNotFoundException(
                    WorkspaceEntity::class, "Workspace with such google calendar id not found", null
                )
            val utilities = if (shouldFindUtilities)
                workspaceRepository.findUtilitiesByWorkspaceId(workspaceEntity.id)
            else emptyList()
            
            return converter.entityToModel(
                workspaceEntity, utilities
            )
        } catch (ex: InstanceNotFoundException) {
            logger.warn("[findWorkspaceById] can't find a workspace with calendar id ${calendarId}. Creating placeholder.")
            return Workspace(null, "placeholder", "placeholder", listOf(), null)
        }
    }

    /**
     * Finds all Google calendar ids
     * @return all calendar ids from database including default
     * */
    fun findAllCalendarsId(): List<String> {
        return db.calendarIds.toList().map { it.calendarId }
    }
}