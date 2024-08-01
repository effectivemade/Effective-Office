package band.effective.office.tv.network.clockify

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.clockify.models.responce.ClockifyResponse
import band.effective.office.tv.network.clockify.models.request.ClockifyRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ClockifyApi {
    @POST("v1/workspaces/{workspaceId}/reports/detailed")
    suspend fun getAllTimeEntriesSport(
        @Path("workspaceId") workspaceId: String,
        @Body clockifyBody: ClockifyRequest
    ) : Either<ErrorReason, ClockifyResponse>
}