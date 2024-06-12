package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingRequestDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.utils.map

class BookingRepositoryImpl(private val api: Api) :
    BookingRepository {
    override suspend fun bookingRoom(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, String> = api.createBooking(eventInfo.toBookingRequestDTO(room))
        .map(errorMapper = { it }, successMapper = { "ok" })

    override suspend fun updateBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, String> =
        api.updateBooking(eventInfo.toBookingRequestDTO(room))
            .map(errorMapper = { it }, successMapper = { "ok" })

    /**Map domain model to DTO*/
    private fun EventInfo.toBookingRequestDTO(room: RoomInfo): BookingRequestDTO =
        BookingRequestDTO(
            beginBooking = this.startTime.timeInMillis,
            endBooking = this.finishTime.timeInMillis,
            ownerEmail = this.organizer.email,
            participantEmails = listOf(this.organizer.email),
            workspaceId = room.id
        )

}


