package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.LocalBookingRepository
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventManager(
    private val networkEventRepository: BookingRepository,
    private val localEventStoreRepository: LocalBookingRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    init {
        scope.launch {
            networkEventRepository.subscribeOnUpdates().collect {
                refreshData()
            }
        }
    }

    fun getEventsFlow() = localEventStoreRepository.subscribeOnUpdates()

    suspend fun refreshData(): Either<ErrorResponse, List<RoomInfo>> {
        val roomInfos = networkEventRepository.getRoomsInfo()
        when (roomInfos) {
            is Either.Success -> localEventStoreRepository.updateRoomsInfo(roomInfos.data)
            else -> {}
        }
        return roomInfos
    }

    suspend fun createBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val roomInfo = getRoomByName(roomName) as? Either.Success
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))

        localEventStoreRepository.createBooking(loadingEvent, roomInfo.data)
        val response = networkEventRepository.createBooking(loadingEvent, roomInfo.data)
        when (response) {
            is Either.Error -> {
                localEventStoreRepository.deleteBooking(loadingEvent, roomInfo.data)
            }
            is Either.Success -> {
                val event = response.data
                localEventStoreRepository.updateBooking(event, roomInfo.data)
            }
        }
        return response
    }

    suspend fun updateBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, EventInfo> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val oldEvent = localEventStoreRepository.getBooking(eventInfo) as? Either.Success
            ?: return Either.Error(ErrorResponse(404, "Old event with id ${eventInfo.id} wasn't found"))
        val roomInfo = getRoomByName(roomName) as? Either.Success
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))

        localEventStoreRepository.updateBooking(loadingEvent, roomInfo.data)
        val response = networkEventRepository.updateBooking(loadingEvent, roomInfo.data)
        when (response) {
            is Either.Error -> {
                localEventStoreRepository.updateBooking(oldEvent.data, roomInfo.data)
            }
            is Either.Success -> {
                val event = response.data
                localEventStoreRepository.updateBooking(event, roomInfo.data)
            }
        }
        return response
    }

    suspend fun deleteBooking(roomName: String, eventInfo: EventInfo): Either<ErrorResponse, String> {
        val loadingEvent = eventInfo.copy(isLoading = true)
        val roomInfo = getRoomByName(roomName) as? Either.Success
            ?: return Either.Error(ErrorResponse(404, "Couldn't find a room with name $roomName"))
        localEventStoreRepository.updateBooking(loadingEvent, roomInfo.data)
        val response = networkEventRepository.deleteBooking(loadingEvent, roomInfo.data)
        when (response) {
            is Either.Error -> {
                localEventStoreRepository.createBooking(eventInfo, roomInfo.data)
            }
            is Either.Success -> {
                localEventStoreRepository.deleteBooking(loadingEvent, roomInfo.data)
            }
        }
        return response
    }

    suspend fun getRoomsInfo(): Either<ErrorResponse, List<RoomInfo>> {
        val roomInfos = localEventStoreRepository.getRoomsInfo()
        if (roomInfos as? Either.Success == null) {
            return refreshData()
        }
        return roomInfos
    }

    suspend fun getCurrentRoomInfos(): Either<ErrorResponse, List<RoomInfo>> {
        return localEventStoreRepository.getRoomsInfo()
    }

    suspend fun getRoomNames() = getRoomsInfo().map(
        errorMapper = { it },
        successMapper = { it.map { room -> room.name } }
    )

    suspend fun getRoomByName(roomName: String) =
        localEventStoreRepository.getRoomsInfo().map(
            errorMapper = { it },
            successMapper = { it.firstOrNull { room -> room.name == roomName } ?: RoomInfo.defaultValue }
        )
}