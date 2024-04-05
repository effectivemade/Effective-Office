package band.effective.office.elevator.domain.models

import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.network.dto.UtilityDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO

data class WorkSpace(
    val id: String,
    val name: String = "",
    val utilities: List<UtilityDTO> = listOf(),
    val zone: WorkspaceZone? = null,
    val workspaceType: WorkspaceType = WorkspaceType.MeetingRoom
)

sealed interface WorkspaceType {
    object MeetingRoom : WorkspaceType

    object RegularSeat : WorkspaceType
}

data class WorkspaceZone(val id: String, val name: String)

fun WorkspaceZone.toUIModelZone() =
    WorkspaceZoneUI(
        isSelected = true,
        name = name
    )

fun List<WorkspaceZone>.toUIModelZones() = map { it.toUIModelZone() }

fun WorkspaceZone.toDTO() =
    WorkspaceZoneDTO(id = id, name = name)

fun List<WorkspaceZoneDTO>.toDomainZone() = map { it.toDomainZone() }
fun WorkspaceZoneDTO.toDomainZone() =
    WorkspaceZone(id = id, name = name)

fun WorkSpace.toDTO() =
    WorkspaceDTO(
        id = id,
        name = name,
        utilities = utilities,
        zone = zone?.toDTO(),
        tag = when (workspaceType) {
            WorkspaceType.MeetingRoom -> "meeting"
            WorkspaceType.RegularSeat -> "regular"
        }
    )

fun WorkspaceDTO.toDomain() =
    WorkSpace(
        id = id,
        name = name,
        utilities = utilities,
        zone = zone?.toDomainZone(),
        workspaceType = when (tag) {
            "regular" -> WorkspaceType.RegularSeat
            "meeting" -> WorkspaceType.MeetingRoom
            else -> WorkspaceType.RegularSeat
        }
    )

fun List<WorkspaceDTO>.toDomain() = map { it.toDomain() }

fun WorkSpace.toUIModel() =
    WorkSpaceUI(
        workSpaceId = id,
        workSpaceName = "${zone?.name.orEmpty()} $name",
        workSpaceType = when (workspaceType) {
            WorkspaceType.RegularSeat -> WorkSpaceType.WORK_PLACE
            WorkspaceType.MeetingRoom -> WorkSpaceType.MEETING_ROOM
        },
        zoneName = zone?.name?:name
    )

fun List<WorkSpace>.toUIModel() = map { it.toUIModel() }