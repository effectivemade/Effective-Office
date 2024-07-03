package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.EventLoadState
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
        eventInfo.loadState = EventLoadState.CREATING
        localStoreRepository.addEvent(roomName, eventInfo)
        val roomInfo = localStoreRepository.getRoomInfo(roomName)
        val response = networkRepository.createBooking(eventInfo, roomInfo)
        when (response) {
            is Either.Error -> {
                localStoreRepository.removeEvent(roomName, eventInfo)
            }
            is Either.Success -> {
                val event = response.data.apply { loadState = EventLoadState.LOADED }
                localStoreRepository.updateEvent(roomName, event)
            }
        }
        return response
    }

    suspend fun updateBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        eventInfo.loadState = EventLoadState.UPDATING
        val oldEvent = localStoreRepository.getEventById(roomName, eventInfo.id)
            ?: return Either.Error(ErrorResponse(404, "Old event with id ${eventInfo.id} wasn't found"))
        localStoreRepository.updateEvent(roomName, eventInfo)
        val roomInfo = localStoreRepository.getRoomInfo(roomName)
        val response = networkRepository.updateBooking(eventInfo, roomInfo)
        when (response) {
            is Either.Error -> {
                localStoreRepository.addEvent(roomName, oldEvent)
            }
            is Either.Success -> {
                val event = response.data.apply { loadState = EventLoadState.LOADED }
                localStoreRepository.updateEvent(roomName, event)
            }
        }
        return response
    }

    suspend fun deleteBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, String> {
        eventInfo.loadState = EventLoadState.DELETING
        localStoreRepository.removeEvent(roomName, eventInfo)
        val response = networkRepository.deleteBooking(eventInfo)
        when (response) {
            is Either.Error -> {
                localStoreRepository.addEvent(roomName, eventInfo)
            }
            is Either.Success -> {}
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