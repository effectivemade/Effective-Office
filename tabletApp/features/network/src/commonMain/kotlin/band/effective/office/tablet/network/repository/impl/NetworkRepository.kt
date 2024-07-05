package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingRequestDTO
import band.effective.office.network.dto.BookingResponseDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.utils.Converter.toOrganizer
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar

class NetworkRepository(
    private val api: Api,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend fun getFreshRoomInfos(): List<RoomInfo>? {
        val start = GregorianCalendar().apply {
            val minutes = get(Calendar.MINUTE)
            val excess = minutes % 15 + 1
            add(Calendar.MINUTE, -excess)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val finish = GregorianCalendar().apply { add(Calendar.DAY_OF_MONTH, 14) }
        val workspaceWithBookings = api.getWorkspacesWithBookings(
            tag = "meeting",
            freeFrom = start.timeInMillis,
            freeUntil =  finish.timeInMillis
        ) as? Either.Success

        val rooms = workspaceWithBookings?.data?.map { it.toRoom() }
        return rooms
    }

    suspend fun createBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, EventInfo> = api.createBooking(eventInfo.toBookingRequestDTO(room))
        .map(errorMapper = { it }, successMapper = { it.toEventInfo() })

    suspend fun updateBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, EventInfo> =
        api.updateBooking(eventInfo.toBookingRequestDTO(room), eventInfo.id)
            .map(errorMapper = { it }, successMapper = { it.toEventInfo() })

    suspend fun deleteBooking(
        eventInfo: EventInfo
    ): Either<ErrorResponse, String> =
        api.deleteBooking(eventInfo.id)
            .map({ it }, { "ok" })

    fun subscribeOnUpdates(refreshCallback: suspend () -> Unit) {
        scope.launch(Dispatchers.IO) {
            api.subscribeOnBookingsList("", this)
                .collectLatest {
                    refreshCallback()
                }
        }
    }

    /**Map domain model to DTO*/
    private fun EventInfo.toBookingRequestDTO(room: RoomInfo): BookingRequestDTO = BookingRequestDTO(
        beginBooking = this.startTime.timeInMillis,
        endBooking = this.finishTime.timeInMillis,
        ownerEmail = this.organizer.email,
        participantEmails = listOf(this.organizer.email),
        workspaceId = room.id
    )

    /**Map DTO to domain model*/
    private fun BookingResponseDTO.toEventInfo(): EventInfo = EventInfo(
        id = id,
        startTime = GregorianCalendar().apply { timeInMillis = beginBooking },
        finishTime = GregorianCalendar().apply { timeInMillis = endBooking },
        organizer = owner?.toOrganizer() ?: Organizer.default
    )

    private fun WorkspaceDTO.toRoom() =
        RoomInfo(
            name = name,
            capacity = utilities.firstOrNull { it.name == "place" }?.count ?: 0,
            isHaveTv = utilities.firstOrNull { it.name == "tv" } != null,
            socketCount = utilities.firstOrNull { it.name == "lan" }?.count ?: 0,
            eventList = bookings?.map { it.toEventInfo() } ?: emptyList(),
            currentEvent = null,
            id = id
        )

}