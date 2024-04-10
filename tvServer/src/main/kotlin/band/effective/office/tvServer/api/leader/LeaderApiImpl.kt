package band.effective.office.tvServer.api.leader

import band.effective.office.tvServer.api.leader.models.eventInfo.EventInfoResponse
import band.effective.office.tvServer.api.leader.models.searchEvent.SearchEventsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class LeaderApiImpl(private val client: HttpClient) : LeaderApi {
    private val baseUrl = "https://leader-id.ru/api/v4/events"
    override suspend fun searchEvents(
        count: Int,
        dateFrom: String,
        dateTo: String,
        cityId: Int,
        placeId: Int
    ): SearchEventsResponse = client.get("$baseUrl/search") {
        parameter("paginationSize", count)
        parameter("dateFrom", dateFrom)
        parameter("dateTo", dateTo)
        parameter("cityId", cityId)
        parameter("placeIds[]", placeId)
    }.body()

    override suspend fun eventInfo(eventId: Int): EventInfoResponse =
        client.get("$baseUrl/${eventId}").body() //api/v4/events/{eventId}
}