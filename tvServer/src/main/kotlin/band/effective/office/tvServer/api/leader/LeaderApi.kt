package band.effective.office.tvServer.api.leader

import band.effective.office.tvServer.api.leader.models.eventInfo.EventInfoResponse
import band.effective.office.tvServer.api.leader.models.searchEvent.SearchEventsResponse

interface LeaderApi {
    suspend fun searchEvents(
        count: Int,
        dateFrom: String,
        dateTo: String,
        cityId: Int,
        placeId: Int
    ): SearchEventsResponse

    suspend fun eventInfo(eventId: Int): EventInfoResponse
}