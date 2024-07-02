package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingResponseDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.Buffer
import band.effective.office.tablet.utils.Converter.toOrganizer
import band.effective.office.tablet.utils.asyncMap
import band.effective.office.tablet.utils.map
import band.effective.office.tablet.utils.unbox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class BufferedRoomRepository(private val api: Api) : RoomRepository {
    private val roomsBuffer = Buffer(
        Either.Error(
            ErrorWithData(
                error = ErrorResponse.getResponse(400),
                saveData = null
            )
        )
    ) { getRoomInfoWithBookings() }

    val mutex = Mutex()
    override suspend fun getRoomsInfo(): Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>> {
        return mutex.withLock {
            roomsBuffer.bufferedValue().map(
                errorMapper = { save -> save.map { it.map { roomInfo -> roomInfo.updateCurrentEvent() } } },
                successMapper = { it.map { roomInfo -> roomInfo.updateCurrentEvent() } }
            )
        }
    }

    override fun subscribeOnUpdates(scope: CoroutineScope): Flow<Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>> {
        scope.launch(Dispatchers.IO) {
            api.subscribeOnBookingsList("", this).collect {
                roomsBuffer.refresh()
            }
        }
        return roomsBuffer.bufferFlow
    }

    override suspend fun updateCashe() {
        roomsBuffer.refresh()
    }

    suspend fun deleteEventFromCash(roomName: String, eventInfo: EventInfo) {
        fun MutableList<RoomInfo>.updList() {
            firstOrNull { it.name == roomName }?.let {
                val index = indexOf(it)
                // this needed for updates because update event
                // components only stores event with new params
                val cachedEvent = it.eventList.first { event ->
                    event == eventInfo
                            || event.id == eventInfo.id
                }
                this[index] = this[index].copy(eventList = it.eventList.minus(cachedEvent))
            }
        }

        val newBufferValue = roomsBuffer.bufferedValue().map(
            errorMapper = { it.map { it.toMutableList().apply { updList() } } },
            successMapper = { it.toMutableList().apply { updList() } }
        )
        roomsBuffer.update(newBufferValue)
    }

    suspend fun getFreshRoomInfo(): Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>> {
        val roomResponse = api.getWorkspaces("meeting")
        val save = roomsBuffer.bufferFlow.replayCache.firstOrNull()?.unbox(
            errorHandler = { it.saveData?.map { it.updateCurrentEvent() } }
        )
        return try {
            when (roomResponse) {
                is Either.Error -> Either.Error(ErrorWithData(roomResponse.error, save))
                is Either.Success -> roomResponse.asyncMap(
                    errorMapper = { it },
                    successMapper = { list ->
                        CoroutineScope(Dispatchers.IO).async {
                            list.map {
                                it.toRoom().addEvents()
                            }
                        }.await()
                    }
                )
            }
        } catch (e: DownloadException) {
            Either.Error(ErrorWithData(e.error, save))
        }
    }

    suspend fun getRoomInfoWithBookings(): Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>> {
        val start = GregorianCalendar()
        val finish = GregorianCalendar().apply { add(Calendar.MONTH, 1) }
        val roomResponse = api.getWorkspacesWithBookings(
            tag = "meeting",
            freeFrom = start.timeInMillis,
            freeUntil = finish.timeInMillis
        )
        val save = roomsBuffer.bufferFlow.replayCache.firstOrNull()?.unbox(
            errorHandler = { it.saveData?.map { it.updateCurrentEvent() } }
        )
        return try {
            when (roomResponse) {
                is Either.Error -> Either.Error(ErrorWithData(roomResponse.error, save))
                is Either.Success -> roomResponse.asyncMap(
                    errorMapper = { it },
                    successMapper = { list ->
                            list.map {
                                it.toRoomWithEvents()
                            }
                    }
                )
            }
        } catch (e: DownloadException) {
            Either.Error(ErrorWithData(e.error, save))
        }
    }

    private suspend fun RoomInfo.addEvents(): RoomInfo {
        val start = GregorianCalendar()
        val finish = GregorianCalendar().apply { add(Calendar.MONTH, 1) }
        val eventResponse = api.getBookingsByWorkspaces(id, start.timeInMillis, finish.timeInMillis)
        val events = when (eventResponse) {
            is Either.Error -> throw DownloadException(eventResponse.error)
            is Either.Success -> eventResponse.data.map { it.toEvent() }
        }
        return copy(eventList = events)
    }

    suspend fun addCachedEvent(roomName: String, eventInfo: EventInfo) {
        fun MutableList<RoomInfo>.updList() {
            firstOrNull { it.name == roomName }?.let {
                val index = indexOf(it)
                this[index] = this[index].copy(eventList = it.eventList.plus(eventInfo))
            }
        }

        val newBufferValue = roomsBuffer.bufferedValue().map(
            errorMapper = { it.map { rooms -> rooms.toMutableList().apply { updList() } } },
            successMapper = { it.toMutableList().apply { updList() } }
        )
        roomsBuffer.update(newBufferValue)
    }

    private fun RoomInfo.updateCurrentEvent(): RoomInfo {
        val now = GregorianCalendar()
        val currentEvent = eventList.firstOrNull {
            it.startTime <= now && it.finishTime > now
        } ?: currentEvent//?.run { if (finishTime > now) this else null }
        return copy(
            eventList = eventList.run {
                if (currentEvent != null) {
                    minus(currentEvent)
                } else this
            },
            currentEvent = currentEvent
        )
    }

    private fun BookingResponseDTO.toEvent() = EventInfo(
        startTime = GregorianCalendar().apply { time = Date(beginBooking) },
        finishTime = GregorianCalendar().apply { time = Date(endBooking) },
        organizer = this.owner?.toOrganizer() ?: Organizer.default ,
        id = id
    )

    private fun WorkspaceDTO.toRoom() =
        RoomInfo(
            name = name,
            capacity = utilities.firstOrNull { it.name == "place" }?.count ?: 0,
            isHaveTv = utilities.firstOrNull { it.name == "tv" } != null,
            socketCount = utilities.firstOrNull { it.name == "lan" }?.count ?: 0,
            eventList = listOf(),
            currentEvent = null,
            id = id
        )
    private fun WorkspaceDTO.toRoomWithEvents() =
        RoomInfo(
            name = name,
            capacity = utilities.firstOrNull { it.name == "place" }?.count ?: 0,
            isHaveTv = utilities.firstOrNull { it.name == "tv" } != null,
            socketCount = utilities.firstOrNull { it.name == "lan" }?.count ?: 0,
            eventList = bookings.let { it?.map{booking -> booking.toEvent()} } ?: listOf(),
            currentEvent = null,
            id = id
        )
    private data class DownloadException(val error: ErrorResponse) : Exception(error.description)
}

