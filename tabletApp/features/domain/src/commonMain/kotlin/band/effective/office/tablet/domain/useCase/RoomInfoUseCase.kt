package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.impl.EventManager
import band.effective.office.tablet.utils.map
import band.effective.office.tablet.utils.unbox
import kotlinx.coroutines.flow.map
import java.util.GregorianCalendar

/**Use case for get info about room*/
class RoomInfoUseCase(private val eventManager: EventManager) {
    /**Get all rooms names*/
    suspend fun getRoomsNames(): List<String> {
        return eventManager.getRoomNames()
    }
    /**Update repository cache*/
    suspend fun updateCache() = eventManager.refreshData()
    /**get info about all rooms*/
    suspend operator fun invoke() = eventManager.getRoomsInfo()
        .map(
            errorMapper = { it },
            successMapper = { it.map { room ->
                room.copy(eventList = room.eventList.filter { event ->
                    event.startTime > GregorianCalendar()
                })
            }}
        )
    /**get update room flow*/
    fun subscribe() =
        eventManager.getEventsFlow().map { either ->
            either.unbox(
                errorHandler = { it.saveData }
            ) ?: emptyList()
        }

    /**Get info about room
     * @param room room name*/
    suspend fun getRoom(room: String) = eventManager.getRoomByName(room)

    suspend fun getCurrentRooms() = eventManager.getCurrentRoomInfos()

    private fun List<RoomInfo>.mapRoomsInfo() =
        map {
            it.copy(eventList = it.eventList.filter {
                event -> event.startTime > GregorianCalendar()
            })
        }

}
