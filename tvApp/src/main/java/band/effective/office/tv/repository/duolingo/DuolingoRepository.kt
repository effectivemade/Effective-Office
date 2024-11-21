package band.effective.office.tv.repository.duolingo

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.workTogether.Teammate
import kotlinx.coroutines.flow.Flow

interface DuolingoRepository {
    suspend fun getUsers(teammates: List<Teammate>): Flow<Either<String, List<DuolingoUser>>>
}