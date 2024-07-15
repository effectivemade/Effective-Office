package band.effective.office.tablet.utils

import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo

object Converter {
    fun RoomInfo.toDto(): WorkspaceDTO =
        WorkspaceDTO(
            id = id,
            name = name,
            utilities = listOf(),
            zone = null,
            tag = "meeting"
        )

    fun Organizer.toDto(): UserDTO =
        UserDTO(
            id = id,
            fullName = fullName,
            active = false,
            role = "",
            avatarUrl = "",
            integrations = null,
            email = email!!,
            tag = "employee"
        )

    fun UserDTO.toOrganizer() = Organizer(fullName = fullName, id = id, email = email)
}