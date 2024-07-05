package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.impl.StateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import java.util.GregorianCalendar

/**Use case for get info about room*/
class RoomInfoUseCase(private val state: StateManager) {
    /**Get all rooms names*/
    fun getRoomsNames(): List<String> {
        return state.getRoomNames()
    }
    /**Update repository cache*/
    suspend fun updateCache() = state.refreshData()
    /**get info about all rooms*/
    suspend operator fun invoke() = state.getRoomInfos().mapRoomsInfo()
    /**get update room flow
     * @param scope scope for collect updates from server*/
    fun subscribe(scope: CoroutineScope) =
        state.getEventsFlow().map { it.mapRoomsInfo() }

    /**Get info about room
     * @param room room name*/
    fun getRoom(room: String) = state.getRoomByName(room)

    fun getCurrentRooms() = state.getCurrentRoomInfos()

    private fun List<RoomInfo>.mapRoomsInfo() =
        map {
            it.copy(eventList = it.eventList.filter {
                event -> event.startTime > GregorianCalendar()
            })
        }

}
