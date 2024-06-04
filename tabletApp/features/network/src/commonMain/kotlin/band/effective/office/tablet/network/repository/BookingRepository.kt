package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
/**Repository for booking room*/
interface BookingRepository {
    /**Create booking
     * @param eventInfo info about new event
     * @param room booking room name
     * @return if booking is created - [EventInfo], else - [ErrorResponse]*/
    suspend fun bookingRoom(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, EventInfo>
    /**Update booking
     * @param eventInfo new info about event
     * @param room booking room name
     * @return if update booking return "ok", else return ErrorResponse*/
    suspend fun updateBooking(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, String>
}