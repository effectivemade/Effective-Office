package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.impl.StateManager

/**Use case for booking room*/
class BookingUseCase(
    private val repository: StateManager
) {
    /**Booking room
     * @param eventInfo info about event
     * @param room room name
     * @return if booking confirm then Either.Success else Either.Error with error code */
    suspend operator fun invoke(
        eventInfo: EventInfo,
        room: String
    ): Either<ErrorResponse, EventInfo> =
        repository.createBooking(room, eventInfo)

    /**Update exist booking
     * @param eventInfo info about event
     * @param room room name
     * @return if booking confirm then Either.Success else Either.Error with error code */
    suspend fun update(eventInfo: EventInfo, room: String) =
        repository.updateBooking(room, eventInfo)

    /**Delete exist booking
     * @param eventInfo info about event
     * @param room room name
     * @return f */
    suspend fun delete(eventInfo: EventInfo, room: String) =
        repository.deleteBooking(room, eventInfo)
}

