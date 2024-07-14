package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.LocalBookingRepository
import band.effective.office.tablet.utils.removeSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.GregorianCalendar

class LocalEventStoreRepository : LocalBookingRepository {
    private val buffer = MutableStateFlow<List<RoomInfo>>(
        emptyList()
    )
    val flow = buffer.asStateFlow()

    override fun subscribeOnUpdates(): Flow<List<RoomInfo>> = flow

    override fun updateRoomsInfo(roomsInfo: List<RoomInfo>) {
        buffer.update { roomsInfo }
    }

    override suspend fun createBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, EventInfo> {
        updateRoomInBuffer(room.name) { events ->
            events + eventInfo
        }
        return Either.Success(eventInfo)
    }

    override suspend fun deleteBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ) : Either<ErrorResponse, String> {
        updateRoomInBuffer(room.name) { events ->
            events - eventInfo
        }
        return Either.Success("ok")
    }

    override suspend fun getBooking(eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        return buffer.value.firstNotNullOfOrNull {
            it.eventList.firstOrNull { event -> event.id == eventInfo.id }
        }?.let { Either.Success(it) }
            ?: Either.Error(
                ErrorResponse(404, "Couldn't find booking with id ${eventInfo.id}")
            )
    }

    override suspend fun updateBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, EventInfo> {
        updateRoomInBuffer(room.name) { events ->
            val oldEvent = events.firstOrNull {
                it.id == eventInfo.id
                        || (it.startTime == eventInfo.startTime
                        && it.finishTime == eventInfo.finishTime)
            } ?: return@updateRoomInBuffer events

            events - oldEvent + eventInfo
        }
        return Either.Success(eventInfo)
    }

    private fun updateRoomInBuffer(roomName: String, action: (List<EventInfo>) -> List<EventInfo>) {
        buffer.update { rooms ->
            val roomIndex = rooms.indexOfFirst { room -> room.name == roomName }
            if (roomIndex == -1) return
            val mutableRooms = rooms.toMutableList()
            val room = mutableRooms[roomIndex]
            val newEvents = action(room.eventList)
            mutableRooms[roomIndex] = room.copy(eventList = newEvents)
            mutableRooms
        }
    }

    override suspend fun getRoomsInfo(): Either<ErrorResponse, List<RoomInfo>> {
        return if (buffer.value.isEmpty())
            Either.Error(ErrorResponse(404, "No rooms were loaded"))
        else Either.Success(buffer.value.map { it.updateCurrentEvent() })
    }

    private fun RoomInfo.removePastEvents(): RoomInfo {
        val now = GregorianCalendar()
        val events = eventList.filter { it.finishTime > now }
        return copy(
            eventList = events
        )
    }

    private fun RoomInfo.updateCurrentEvent(): RoomInfo {
        val now = GregorianCalendar().removeSeconds()
        val currentEvent = eventList.firstOrNull {
            it.startTime <= now && it.finishTime > now
                    && !it.isLoading
        } ?: return this

        return copy(
            eventList = eventList - currentEvent,
            currentEvent = currentEvent
        )
    }
}