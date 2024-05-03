package office.effective.features.workspace.converters

import office.effective.common.utils.UuidValidator
import office.effective.dto.*
import office.effective.model.Utility
import office.effective.model.Workspace
import office.effective.model.WorkspaceZone
import java.util.*

/**
 * Converts between [WorkspaceDTO] (with contained [WorkspaceZoneDTO] and [UtilityDTO]s)
 * and [Workspace] (with contained [WorkspaceZone] and [Utilities][Utility]) objects.
 *
 * This converter helps in the transformation of data between the DTO and model representations of a workspace.
 */
class WorkspaceDtoModelConverter(private val uuidValidator: UuidValidator) {

    /**
     * Converts [Workspace] with [WorkspaceZone] and [Utilities][Utility]
     * to [WorkspaceDTO] with [WorkspaceZoneDTO] and [UtilityDTO]s
     * @param model The [Workspace] object to be converted
     * @param bookings bookings of this workspace
     * @return The resulting [WorkspaceDTO] object
     * @author Daniil Zavyalov
     */
    fun modelToDto(model: Workspace, bookings: List<BookingResponseDTO>? = null): WorkspaceDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceDTO(
            id = model.id.toString(),
            name = model.name,
            utilities = utilities,
            zone = model.zone?.let { zoneModelToDto(it) },
            tag = model.tag,
            bookings = bookings
        )
    }

    /**
     * Converts [Workspace] with [WorkspaceZone] and [Utilities][Utility]
     * to [WorkspaceDTO] with [WorkspaceZoneDTO] and [UtilityDTO]s
     * @param model The [Workspace] object to be converted
     * @return The resulting [WorkspaceDTO] object
     * @author Daniil Zavyalov
     */
    @Deprecated(
        message = "Deprecated since 1.0 api version",
        replaceWith = ReplaceWith("modelToResponseDto(model)")
    )
    fun modelToResponseDto(model: Workspace): WorkspaceResponseDTO {
        val utilities = model.utilities.map { utilityModelToDto(it) }
        return WorkspaceResponseDTO(
            id = model.id.toString(),
            name = model.name,
            utilities = utilities,
            zone = model.zone?.let { zoneModelToDto(it) },
            tag = model.tag,
        )
    }

    /**
     * Converts [Utility] to [UtilityDTO]
     *
     * @param model The [Utility] object to be converted
     * @return The resulting [UtilityDTO] object
     * @author Daniil Zavyalov
     */
    private fun utilityModelToDto(model: Utility): UtilityDTO {
        return UtilityDTO(model.id.toString(), model.name, model.iconUrl, model.count)
    }

    /**
     * Converts [WorkspaceZone] to [WorkspaceZoneDTO]
     *
     * @param model The [WorkspaceZone] object to be converted
     * @return The resulting [WorkspaceZoneDTO] object
     * @author Daniil Zavyalov
     */
    fun zoneModelToDto(model: WorkspaceZone): WorkspaceZoneDTO {
        return WorkspaceZoneDTO(model.id.toString(), model.name)
    }

    /**
     * Converts [Workspace] with [WorkspaceZone] and [Utilities][Utility]
     * to [WorkspaceDTO] with [WorkspaceZoneDTO] and [UtilityDTO]s.
     * Uses [UuidValidator] to convert workspace id to UUID, but if [WorkspaceDTO.id]=="null" [Workspace.id] will be null
     * @param dto The WorkspaceDTO object to be converted
     * @return The resulting Workspace object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun dtoToModel(dto: WorkspaceDTO): Workspace {
        var workspaceId: UUID? = null
        if (dto.id != "null") {
            workspaceId = uuidValidator.uuidFromString(dto.id)
        }

        val utilities = dto.utilities.map { utilityDtoToModel(it) }
        return Workspace(
            id = workspaceId,
            name = dto.name,
            tag = dto.tag,
            utilities = utilities,
            zone = dto.zone?.let { zoneDtoToModel(it) }
        )
    }

    /**
     * Converts [Workspace] with [WorkspaceZone] and [Utilities][Utility]
     * to [WorkspaceResponseDTO] with [WorkspaceZoneDTO] and [UtilityDTO]s.
     * Uses [UuidValidator] to convert workspace id to UUID, but if [WorkspaceResponseDTO.id]=="null" [Workspace.id] will be null
     * @param dto The WorkspaceDTO object to be converted
     * @return The resulting Workspace object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    @Deprecated(
        message = "Deprecated since 1.0 api version",
        replaceWith = ReplaceWith("responseDtoToModel(model)")
    )
    fun responseDtoToModel(dto: WorkspaceResponseDTO): Workspace {
        var workspaceId: UUID? = null
        if (dto.id != "null") {
            workspaceId = uuidValidator.uuidFromString(dto.id)
        }

        val utilities = dto.utilities.map { utilityDtoToModel(it) }
        return Workspace(
            id = workspaceId,
            name = dto.name,
            tag = dto.tag,
            utilities = utilities,
            zone = dto.zone?.let { zoneDtoToModel(it) }
        )
    }

    /**
     * Converts [UtilityDTO] to [Utility]
     *
     * @param dto The [UtilityDTO] object to be converted
     * @return The resulting [Utility] object
     * @author Daniil Zavyalov
     */
    private fun utilityDtoToModel(dto: UtilityDTO): Utility {
        return Utility(uuidValidator.uuidFromString(dto.id), dto.name, dto.iconUrl, dto.count)
    }

    /**
     * Converts [WorkspaceZoneDTO] to [WorkspaceZone]
     *
     * @param dto The [WorkspaceZoneDTO] object to be converted
     * @return The resulting [WorkspaceZone] object
     * @author Daniil Zavyalov
     */
    private fun zoneDtoToModel(dto: WorkspaceZoneDTO): WorkspaceZone {
        return WorkspaceZone(uuidValidator.uuidFromString(dto.id), dto.name)
    }
}