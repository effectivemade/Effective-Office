package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.utils.removeSeconds

/**Use case for checking booking room opportunity*/
class CheckBookingUseCase(
    private val roomInfoUseCase: RoomInfoUseCase
) {
    /** get event blocking room for booking
     * @param event info about event
     * @param room room name
     * @return Event busy with room booking, if room free, return null*/
    suspend operator fun invoke(event: EventInfo, room: String) =
        busyEvents(event, room)

    /** get events blocking room for booking
     * @param event info about event
     * @param room room name
     * @return List events busy with room booking, if room's free then empty list will be returned*/
    suspend fun busyEvents(event: EventInfo, room: String): List<EventInfo> {
        val eventList = eventList(room)
        return eventList.getBusy(event)
    }

    private suspend fun eventList(room: String): List<EventInfo> {
        val roomInfo = roomInfoUseCase.getRoom(room)
        return roomInfo?.getAllEvents() ?: emptyList()
    }

    /**
     * @return True, if the moment belongs to the time interval between event start and end*/
    private fun EventInfo.collidesWith(event: EventInfo) =
        this.startTime.removeSeconds() < event.finishTime.removeSeconds()
                && this.finishTime.removeSeconds() > event.startTime.removeSeconds()

    private fun RoomInfo.getAllEvents(): List<EventInfo> =
        if (currentEvent != null) eventList + currentEvent!! else eventList

    private fun List<EventInfo>.getBusy(event: EventInfo) =
        filter { it.collidesWith(event) }
}