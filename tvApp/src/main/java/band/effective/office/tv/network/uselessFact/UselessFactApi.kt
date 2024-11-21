package band.effective.office.tv.network.uselessFact

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.uselessFact.model.FactDTO
import retrofit2.http.GET

interface UselessFactApi {
    @GET("facts/random")
    suspend fun getFact(): Either<ErrorReason,FactDTO>
}