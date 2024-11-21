package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.minutes

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

    /** Get nearest free room and time until room is released, if all rooms are busy
     * @param rooms list of all rooms
     * @param minDuration event duration in minutes */
    fun getNearestFreeRoom(rooms: List<RoomInfo>, minDuration: Int): Pair<RoomInfo, Long> {
        val currentTime = GregorianCalendar().timeInMillis
        return rooms.map { room ->
            room.getNearestFreeTime(minDuration).let {
                room to (it.timeInMillis - currentTime)
            }
        }.minBy { it.second }
    }

    private fun RoomInfo.getNearestFreeTime(minDuration: Int): Calendar {
        var nearestTime = currentEvent?.finishTime ?: GregorianCalendar()

        if (eventList.isEmpty()) return currentEvent!!.finishTime
        if (nearestTime.timeInMillis + minDuration.minutes.inWholeMilliseconds < eventList.first().startTime.timeInMillis)
            return nearestTime

        for ((index, event) in eventList.withIndex()) {
            if (index != eventList.lastIndex) {
                if (event.finishTime.timeInMillis + minDuration.minutes.inWholeMilliseconds < eventList[index + 1].startTime.timeInMillis)
                    return event.finishTime
            }
            nearestTime = event.finishTime
        }
        return nearestTime
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
