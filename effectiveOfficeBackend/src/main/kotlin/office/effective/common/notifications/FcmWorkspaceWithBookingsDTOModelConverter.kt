package office.effective.common.notifications

import office.effective.dto.fcm.FcmEventDTO
import office.effective.dto.fcm.FcmOrganizerDTO
import office.effective.dto.fcm.FcmWorkspaceWithBookingsDTO
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Workspace

/**
 * Converts the [Workspace] and list of its [Booking]
 * to [FcmWorkspaceWithBookingsDTO]
 */
object FcmWorkspaceWithBookingsDTOModelConverter {
    /**
     * Converts the [Workspace] and list of its [Booking]
     * to [FcmWorkspaceWithBookingsDTO]
     * @param workspace [Workspace] changed workspace
     * @param bookings booking of workspace
     * @return @return resulting [FcmWorkspaceWithBookingsDTO] object
     */
    fun fromModelsToDTO(workspace: Workspace, bookings: List<Booking>): FcmWorkspaceWithBookingsDTO {
        return FcmWorkspaceWithBookingsDTO(
            id = workspace.id.toString(),
            bookings = bookings.map { booking -> bookingModelToFcmEventDTO(booking) }
        )
    }
    
    private fun bookingModelToFcmEventDTO(booking: Booking): FcmEventDTO {
        return FcmEventDTO(
            id = booking.id,
            startTime = booking.beginBooking.toEpochMilli(),
            endTime = booking.endBooking.toEpochMilli(),
            organizer = booking.owner?.let { userModelToFcmOrganizer(it) }
        )
    }
    
    private fun userModelToFcmOrganizer(user: UserModel): FcmOrganizerDTO {
        return FcmOrganizerDTO(
            id = user.id.toString(),
            email = user.email,
            fullName = user.fullName
        )
    }
}