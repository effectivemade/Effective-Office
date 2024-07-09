package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo

class EventManager(
    private val networkEventRepository: NetworkEventRepository,
    private val localEventStoreRepository: LocalEventStoreRepository
) {
    init {
        networkEventRepository.subscribeOnUpdates { refreshData() }
    }

    fun getEventsFlow() = localEventStoreRepository.flow

    suspend fun refreshData() {
        val roomInfos = networkEventRepository.getFreshRoomsInfo()
        roomInfos?.let { localEventStoreRepository.updateRoomInfos(it) }
            ?: localEventStoreRepository.updateCurrentEvents()
    }

    suspend fun createBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val roomInfo = localEventStoreRepository.getRoomByName(roomName)
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))

        localEventStoreRepository.addEvent(roomName, loadingEvent)
        val response = networkEventRepository.createBooking(loadingEvent, roomInfo)
        when (response) {
            is Either.Error -> {
                localEventStoreRepository.removeEvent(roomName, loadingEvent)
            }
            is Either.Success -> {
                val event = response.data
                localEventStoreRepository.updateEvent(roomName, event)
            }
        }
        return response
    }

    suspend fun updateBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val oldEvent = localEventStoreRepository.getEventById(roomName, eventInfo.id)
            ?: return Either.Error(ErrorResponse(404, "Old event with id ${eventInfo.id} wasn't found"))
        val roomInfo = localEventStoreRepository.getRoomByName(roomName)
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))

        localEventStoreRepository.updateEvent(roomName, loadingEvent)
        val response = networkEventRepository.updateBooking(loadingEvent, roomInfo)
        when (response) {
            is Either.Error -> {
                localEventStoreRepository.updateEvent(roomName, oldEvent)
            }
            is Either.Success -> {
                val event = response.data
                localEventStoreRepository.updateEvent(roomName, event)
            }
        }
        return response
    }

    suspend fun deleteBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, String> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        localEventStoreRepository.updateEvent(roomName, loadingEvent)
        val response = networkEventRepository.deleteBooking(loadingEvent)
        when (response) {
            is Either.Error -> {
                localEventStoreRepository.addEvent(roomName, eventInfo)
            }
            is Either.Success -> {
                localEventStoreRepository.removeEvent(roomName, loadingEvent)
            }
        }
        return response
    }

    suspend fun getRoomsInfo(): List<RoomInfo> {
        val roomInfos = localEventStoreRepository.getRoomsInfo()
        if (roomInfos.isEmpty()) {
            refreshData()
            return localEventStoreRepository.getRoomsInfo()
        }
        return roomInfos
    }

    fun getCurrentRoomInfos(): List<RoomInfo> {
        return localEventStoreRepository.getRoomsInfo()
    }

    fun getRoomNames() = localEventStoreRepository.getRoomNames()

    fun getRoomByName(roomName: String) =
        localEventStoreRepository.getRoomByName(roomName)
}