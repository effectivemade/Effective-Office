package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.utils.removeSeconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.GregorianCalendar

class LocalStoreRepository {
    private val buffer = MutableStateFlow<List<RoomInfo>>(
        listOf()
    )
    val flow = buffer.asStateFlow()

    fun getRoomInfo(roomName: String): RoomInfo {
        return buffer.value.first { it.name == roomName }
    }

    fun updateCurrentEvents() {
        buffer.update {
            it.map { room -> room.removePastEvents() }
        }
    }

    fun updateRoomInfos(roomInfos: List<RoomInfo>) {
        buffer.update {
            roomInfos.toMutableList()
        }
    }

    fun addEvent(roomName: String, eventInfo: EventInfo) {
        buffer.update { rooms ->
            val roomIndex = rooms.indexOfFirst { room -> room.name == roomName }
            if (roomIndex == -1) return
            val mutableRooms = rooms.toMutableList()
            val room = mutableRooms[roomIndex]
            val events = room.eventList + eventInfo
            mutableRooms[roomIndex] = room.copy(eventList = events)
            mutableRooms
        }
    }

    fun removeEvent(roomName: String, eventInfo: EventInfo) {
        buffer.update { rooms ->
            val roomIndex = rooms.indexOfFirst { room -> room.name == roomName }
            if (roomIndex == -1) return
            val mutableRooms = rooms.toMutableList()
            val room = mutableRooms[roomIndex]
            val events = room.eventList - eventInfo
            mutableRooms[roomIndex] = room.copy(eventList = events)
            mutableRooms
        }
    }

    fun updateEvent(roomName: String, eventInfo: EventInfo) {
        buffer.update { rooms ->
            val roomIndex = rooms.indexOfFirst { room -> room.name == roomName }
            if (roomIndex == -1) return
            val mutableRooms = rooms.toMutableList()
            val room = mutableRooms[roomIndex]
            val oldEvent = room.eventList.firstOrNull {
                it.id == eventInfo.id
                        || (it.startTime == eventInfo.startTime
                        && it.finishTime == eventInfo.finishTime)
            } ?: return

            val events = room.eventList - oldEvent + eventInfo
            mutableRooms[roomIndex] = room.copy(eventList = events)
            mutableRooms
        }
    }

    fun getRooms(): List<RoomInfo> =
        buffer.value.map { it.updateCurrentEvent() }

    fun getRoomNames(): List<String> {
        return buffer.value.map { it.name }
    }

    fun getEventById(roomName: String, eventId: String): EventInfo? {
        val room = buffer.value.firstOrNull { room -> room.name == roomName }
        return room?.eventList?.firstOrNull { it.id == eventId }
    }

    fun getRoomByName(roomName: String): RoomInfo? =
        buffer.value.firstOrNull { it.name == roomName }

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
        } ?: return this

        return copy(
            eventList = eventList - currentEvent,
            currentEvent = currentEvent
        )
    }
}