package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.utils.removeSeconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.GregorianCalendar

class LocalEventStoreRepository {
    private val buffer = MutableStateFlow<List<RoomInfo>>(
        emptyList()
    )
    val flow = buffer.asStateFlow()

    fun getRoomByName(roomName: String): RoomInfo? {
        return buffer.value.firstOrNull { it.name == roomName }
    }

    fun updateCurrentEvents() {
        buffer.update {
            it.map { room -> room.removePastEvents() }
        }
    }

    fun updateRoomInfos(roomInfos: List<RoomInfo>) {
        buffer.update { roomInfos }
    }

    fun addEvent(roomName: String, eventInfo: EventInfo) {
        updateRoomInBuffer(roomName) { events ->
            events + eventInfo
        }
    }

    fun removeEvent(roomName: String, eventInfo: EventInfo) {
        updateRoomInBuffer(roomName) { events ->
            events - eventInfo
        }
    }

    fun updateEvent(roomName: String, eventInfo: EventInfo) {
        updateRoomInBuffer(roomName) { events ->
            val oldEvent = events.firstOrNull {
                it.id == eventInfo.id
                        || (it.startTime == eventInfo.startTime
                        && it.finishTime == eventInfo.finishTime)
            } ?: return@updateRoomInBuffer events

            events - oldEvent + eventInfo
        }
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

    fun getRoomsInfo(): List<RoomInfo> =
        buffer.value.map { it.updateCurrentEvent() }

    fun getRoomNames(): List<String> {
        return buffer.value.map { it.name }
    }

    fun getEventById(roomName: String, eventId: String): EventInfo? {
        val room = getRoomByName(roomName)
        return room?.eventList?.firstOrNull { it.id == eventId }
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