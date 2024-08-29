package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.workTogether.Teammate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DuolingoDataUseCase @Inject constructor(
    private val duolingoRepository: DuolingoRepository
) {
    suspend fun getDuolingoDataForStories(teammates:  List<Teammate>): Flow<Either<String, List<DuolingoUser>>> {
        return duolingoRepository.getUsers(teammates)
            .map {
                when (it) {
                    is Either.Success -> Either.Success(it.data)
                    is Either.Failure -> it
                }
            }
    }
}