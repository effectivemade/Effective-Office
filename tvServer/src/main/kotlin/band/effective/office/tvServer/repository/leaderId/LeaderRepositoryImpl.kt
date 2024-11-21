package band.effective.office.tvServer.repository.leaderId

import band.effective.office.tvServer.api.leader.LeaderApi
import band.effective.office.tvServer.api.leader.models.eventInfo.EventInfoResponse
import band.effective.office.tvServer.model.LeaderIdEventInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LeaderRepositoryImpl(private val api: LeaderApi) : LeaderRepository {
    override suspend fun getEventsInfo(finishDate: LocalDate, cityId: Int, placeId: Int): Flow<LeaderIdEventInfo> =
        flow {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val currentDay = LocalDate.now()
            val eventIds = api.searchEvents(
                count = 100,
                dateFrom = currentDay.format(formatter),
                dateTo = finishDate.format(formatter),
                cityId = cityId,
                placeId = placeId
            ).data.items.map { it.id }
            eventIds.forEach { id ->
                emit(api.eventInfo(id).toEventInfo())
            }
        }

    private fun EventInfoResponse.toEventInfo() = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").let { formatter ->
        LeaderIdEventInfo(
            id = data.id,
            name = data.fullName,
            startDateTime = LocalDateTime.parse(data.dateStart, formatter),
            finishDateTime = LocalDateTime.parse(data.dateEnd, formatter),
            isOnline = data.status == "online",
            photoUrl = data.photo,
            organizer = data.organizers.first().name,
            speakers = data.speakers.map { "${it.user.firstName} ${it.user.lastName}" },
            endRegDate = LocalDateTime.parse(data.registrationDateEnd, formatter)
        )
    }
}
