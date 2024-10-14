package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.clockify.ClockifyUser
import band.effective.office.tv.repository.clockify.ClockifyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClockifyDataUseCase @Inject constructor(
    private val clockifyRepository: ClockifyRepository
) {
    suspend fun getClockifyDataForStories(): Flow<Either<String, List<ClockifyUser>>> {
        return clockifyRepository.getTimeEntries()
            .map {
                when (it) {
                    is Either.Success -> Either.Success(it.data)
                    is Either.Failure -> it
                }
            }
    }
}