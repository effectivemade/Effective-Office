package office.effective.features.workspace.facade

import office.effective.common.constants.BookingConstants
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.ValidationException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.dto.WorkspaceDTO
import office.effective.dto.WorkspaceZoneDTO
import office.effective.features.booking.facade.BookingFacadeV1
import office.effective.features.booking.service.BookingService
import office.effective.model.Workspace
import office.effective.serviceapi.IWorkspaceService
import java.time.Instant

/**
 * Class used in routes to handle workspaces requests.
 * Provides business transaction, data conversion and validation.
 *
 * In case of an error, the database transaction will be rolled back.
 */
class WorkspaceFacadeV1(
    private val service: IWorkspaceService,
    private val converter: WorkspaceFacadeConverter,
    private val transactionManager: DatabaseTransactionManager,
    private val uuidValidator: UuidValidator,
    private val bookingFacade: BookingFacadeV1
) {

    /**
     * Retrieves a [WorkspaceDTO] by its id
     *
     * @param id id of requested workspace. Should be valid UUID
     * @return [WorkspaceDTO] with the given [id]
     * @throws InstanceNotFoundException if workspace with the given id doesn't exist in database
     * @author Daniil Zavyalov
     */
    fun findById(id: String): WorkspaceDTO {
        val uuid = uuidValidator.uuidFromString(id)

        val workspaceDTO: WorkspaceDTO = transactionManager.useTransaction({
            val workspace = service.findById(uuid)
                ?: throw InstanceNotFoundException(Workspace::class, "Workspace with id $id not found", uuid)
            workspace.let { converter.modelToDto(it) }
        })

        return workspaceDTO
    }

    /**
     * Returns all [WorkspaceDTO] with the given tag
     *
     * @param tag tag name of requested workspaces
     * @return List of [WorkspaceDTO] with the given [tag]
     * @author Daniil Zavyalov
     */
    fun findAllByTag(
        tag: String,
        withBookingsFrom: Long? = null,
        withBookingsUntil: Long? = null
    ): List<WorkspaceDTO> {
        if (withBookingsFrom != null && withBookingsUntil != null) {
            if (withBookingsFrom < 0L || withBookingsFrom >= BookingConstants.MAX_TIMESTAMP)
                throw ValidationException("with_bookings_from should be non-negative and greater than timestamp max value")
            else if (withBookingsUntil < 0L || withBookingsUntil >= BookingConstants.MAX_TIMESTAMP)
                throw ValidationException("with_bookings_until should be non-negative and less than timestamp max value")
            else if (withBookingsUntil <= withBookingsFrom)
                throw ValidationException(
                    "with_bookings_until (${withBookingsUntil}) should be greater than with_bookings_from (${withBookingsFrom})")

            return findAllByTag(tag).map { workspace ->
                val bookings = bookingFacade.findAll(
                    userId = null,
                    workspaceId = workspace.id.toString(),
                    bookingRangeFrom = withBookingsFrom,
                    bookingRangeTo = withBookingsUntil
                )
                converter.modelToDto(workspace, bookings)
            }
        }
        return findAllByTag(tag).map { workspace ->
            converter.modelToDto(workspace)
        }
    }

    /**
     * Returns all [Workspace] with the given tag
     *
     * @param tag tag name of requested workspaces
     * @return List of [Workspace] with the given [tag]
     * @author Daniil Zavyalov
     */
    private fun findAllByTag(tag: String): List<Workspace> {
        return transactionManager.useTransaction({
            service.findAllByTag(tag)
        })
    }

    /**
     * Returns all [Workspace]s with the given tag which are free during the given period
     *
     * @param tag tag name of requested workspaces
     * @param beginTimestamp period start time
     * @param endTimestamp period end time
     * @return List of [WorkspaceDTO] with the given [tag]
     * @throws ValidationException if begin or end timestamp less than 0, greater than max timestamp
     * or if end timestamp less than or equal to begin timestamp
     * @author Daniil Zavyalov
     */
    fun findAllFreeByPeriod(tag: String, beginTimestamp: Long, endTimestamp: Long): List<WorkspaceDTO> {
        if (beginTimestamp < 0L || beginTimestamp >= BookingConstants.MAX_TIMESTAMP)
            throw ValidationException("Begin timestamp should be non-negative and less than timestamp max value")
        else if (endTimestamp < 0L || endTimestamp >= BookingConstants.MAX_TIMESTAMP)
            throw ValidationException("End timestamp should be non-negative and less than timestamp max value")
        else if (endTimestamp <= beginTimestamp)
            throw ValidationException(
                "End timestamp (${endTimestamp}) should be greater than begin timestamp (${beginTimestamp})")

        return transactionManager.useTransaction({
            val modelList = service.findAllFreeByPeriod(
                tag,
                Instant.ofEpochMilli(beginTimestamp),
                Instant.ofEpochMilli(endTimestamp)
            )
            modelList.map { converter.modelToDto(it) }
        })
    }

    /**
     * Returns all workspace zones
     *
     * @return List of all [WorkspaceZoneDTO]
     * @author Daniil Zavyalov
     */
    fun findAllZones(): List<WorkspaceZoneDTO> {
        return transactionManager.useTransaction({
            service.findAllZones().map { converter.zoneModelToDto(it) }
        })
    }
}