package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import kotlinx.coroutines.flow.Flow

/**Repository for booking room*/
interface BookingRepository {
    /**Create booking
     * @param eventInfo info about new event
     * @param room booking room name
     * @return if booking is created - [EventInfo], else - [ErrorResponse]*/
    suspend fun createBooking(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, EventInfo>

    /**Update booking
     * @param eventInfo new info about event
     * @param room booking room name
     * @return if booking is updated - [EventInfo], else - [ErrorResponse]*/
    suspend fun updateBooking(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, EventInfo>

    /**Update booking
     * @param eventInfo new info about event
     * @return if booking is updated - "ok", else - [ErrorResponse]*/
    suspend fun deleteBooking(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, String>

    suspend fun getBooking(eventInfo: EventInfo): Either<ErrorResponse, EventInfo>

    fun subscribeOnUpdates(): Flow<Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>>

    suspend fun getRoomsInfo(): Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>
}