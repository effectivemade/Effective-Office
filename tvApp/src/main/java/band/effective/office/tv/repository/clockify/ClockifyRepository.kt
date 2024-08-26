package band.effective.office.tv.repository.clockify

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.clockify.ClockifyUser
import kotlinx.coroutines.flow.Flow

interface ClockifyRepository {
    suspend fun getTimeEntries() : Flow<Either<String, List<ClockifyUser>>>
}