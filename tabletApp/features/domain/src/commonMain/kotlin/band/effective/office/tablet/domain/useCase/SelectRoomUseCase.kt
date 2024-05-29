package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.math.absoluteValue

/** Use case for choosing a room with similar capacity*/
open class SelectRoomUseCase { // NOTE(Maksim Mishenko) class open for override select room logic
    /** Get room with similar capacity or current room if it empty
     * @param currentRoom current room info
     * @param rooms all rooms
     * @param minEventDuration event duration in minutes
     * @return Room with similar current room capacity, or null if all rooms booking*/
    open fun getRoom(currentRoom: RoomInfo, rooms: List<RoomInfo>, minEventDuration: Int): RoomInfo? {
        val candidates = rooms.filter { it.isFreeOn(minEventDuration) }
            .sortedBy { (it.capacity - currentRoom.capacity).absoluteValue }
        return if (candidates.contains(currentRoom)) currentRoom else candidates.firstOrNull()
    }

    private fun RoomInfo.isFreeOn(duration: Int): Boolean {
        if (currentEvent != null) return false
        if (eventList.isEmpty()) return true
        return eventList.first().startTime > GregorianCalendar().apply {
            add(
                Calendar.MINUTE,
                duration
            )
        }
    }
}
