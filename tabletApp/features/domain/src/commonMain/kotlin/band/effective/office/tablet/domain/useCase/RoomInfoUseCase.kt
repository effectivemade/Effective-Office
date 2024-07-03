package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.impl.StateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import java.util.GregorianCalendar

/**Use case for get info about room*/
class RoomInfoUseCase(private val repository: StateManager) {
    /**Get all rooms names*/
    fun getRoomsNames(): List<String> {
        return repository.getRoomNames()
    }
    /**Update repository cache*/
    suspend fun updateCache() = repository.refreshData()
    /**get info about all rooms*/
    suspend operator fun invoke() = repository.getRoomInfos().mapRoomsInfo()
    /**get update room flow
     * @param scope scope for collect updates from server*/
    fun subscribe(scope: CoroutineScope) =
        repository.getEventsFlow().map { it.mapRoomsInfo() }

    /**Get info about room
     * @param room room name*/
    fun getRoom(room: String) = repository.getRoomByName(room)

    fun getCurrentRooms() = repository.getCurrentRoomInfos()

    private fun List<RoomInfo>.mapRoomsInfo() =
        map {
            it.copy(eventList = it.eventList.filter {
                event -> event.startTime > GregorianCalendar()
            })
        }

}
