package band.effective.office.tv.network.leader

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.leader.models.eventInfo.EventInfoResponse
import band.effective.office.tv.network.leader.models.searchEvent.SearchEventsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeaderApi {
    @GET("api/v4/events/search")
    suspend fun searchEvents(
        @Query("paginationSize") count: Int,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String,
        @Query("cityId") cityId: Int,
        @Query("placeIds[]") placeId: Int
    ): Either<ErrorReason, SearchEventsResponse>

    @GET("api/v4/events/{eventId}")
    suspend fun eventInfo(@Path("eventId") eventId: Int): Either<ErrorReason, EventInfoResponse>
}