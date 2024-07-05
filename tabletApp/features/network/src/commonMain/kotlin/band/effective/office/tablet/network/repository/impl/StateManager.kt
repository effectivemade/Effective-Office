package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo

class StateManager(
    private val networkRepository: NetworkRepository,
    private val localStoreRepository: LocalStoreRepository
) {
    init {
        networkRepository.subscribeOnUpdates { refreshData() }
    }

    fun getEventsFlow() = localStoreRepository.flow

    suspend fun refreshData() {
        val roomInfos = networkRepository.getFreshRoomInfos()
        roomInfos?.let { localStoreRepository.updateRoomInfos(it) }
            ?: localStoreRepository.updateCurrentEvents()
    }

    suspend fun createBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val roomInfo = localStoreRepository.getRoomByName(roomName)
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))

        localStoreRepository.addEvent(roomName, loadingEvent)
        val response = networkRepository.createBooking(loadingEvent, roomInfo)
        when (response) {
            is Either.Error -> {
                localStoreRepository.removeEvent(roomName, loadingEvent)
            }
            is Either.Success -> {
                val event = response.data
                localStoreRepository.updateEvent(roomName, event)
            }
        }
        return response
    }

    suspend fun updateBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val oldEvent = localStoreRepository.getEventById(roomName, eventInfo.id)
            ?: return Either.Error(ErrorResponse(404, "Old event with id ${eventInfo.id} wasn't found"))
        val roomInfo = localStoreRepository.getRoomByName(roomName)
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))

        localStoreRepository.updateEvent(roomName, loadingEvent)
        val response = networkRepository.updateBooking(loadingEvent, roomInfo)
        when (response) {
            is Either.Error -> {
                localStoreRepository.updateEvent(roomName, oldEvent)
            }
            is Either.Success -> {
                val event = response.data
                localStoreRepository.updateEvent(roomName, event)
            }
        }
        return response
    }

    suspend fun deleteBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, String> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        localStoreRepository.updateEvent(roomName, loadingEvent)
        val response = networkRepository.deleteBooking(loadingEvent)
        when (response) {
            is Either.Error -> {
                localStoreRepository.addEvent(roomName, eventInfo)
            }
            is Either.Success -> {
                localStoreRepository.removeEvent(roomName, loadingEvent)
            }
        }
        return response
    }

    suspend fun getRoomInfos(): List<RoomInfo> {
        val roomInfos = localStoreRepository.getRooms()
        if (roomInfos.isEmpty()) {
            refreshData()
            return localStoreRepository.getRooms()
        }
        return roomInfos
    }

    fun getCurrentRoomInfos(): List<RoomInfo> {
        return localStoreRepository.getRooms()
    }

    fun getRoomNames() = localStoreRepository.getRoomNames()

    fun getRoomByName(roomName: String) =
        localStoreRepository.getRoomByName(roomName)
}