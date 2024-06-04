package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map

/**Use case for booking room*/
class BookingUseCase(
    private val repository: BookingRepository,
    private val roomRepository: RoomRepository
) {
    /**Get info about room
     * @param room room name*/
    suspend fun getRoom(room: String) = try {
        roomRepository.getRoomsInfo().map(
            errorMapper = { it.error },
            successMapper = { it.first { roomInfo -> roomInfo.name == room } }
        )
    } catch (e: NoSuchElementException) {
        Either.Error(ErrorResponse.getResponse(404))
    }
    /**Booking room
     * @param eventInfo info about event
     * @param room room name
     * @return if booking confirm then Either.Success else Either.Error with error code */
    suspend operator fun invoke(
        eventInfo: EventInfo,
        room: String
    ): Either<ErrorResponse, EventInfo> =
        with(getRoom(room)) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> repository.bookingRoom(eventInfo, data)
            }
        }
    /**Update exist booking
     * @param eventInfo info about event
     * @param room room name
     * @return if booking confirm then Either.Success else Either.Error with error code */
    suspend fun update(eventInfo: EventInfo, room: String) =
        with(getRoom(room)) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> repository.updateBooking(
                    eventInfo = eventInfo,
                    room = data
                )
            }
        }
}