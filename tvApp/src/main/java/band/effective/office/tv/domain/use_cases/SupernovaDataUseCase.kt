package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.repository.supernova.SupernovaRepository
import band.effective.office.tv.repository.supernova.Talent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SupernovaDataUseCase @Inject constructor(
    val supernova: SupernovaRepository
) {
    suspend fun getSupernovaData(): Flow<Either<String, List<Talent>>> {
        val supernovaData = flow {
            val response = try {
                val talents = supernova.getTalents()
                Either.Success(talents)
            } catch (e: Throwable) {
                Either.Failure(
                    e.message ?: "Error in EventStoryDataCombinerUseCase.getNotionDataForStories"
                )
            }
            emit(response)
        }
        return supernovaData.map {
            when (it) {
                is Either.Success -> Either.Success(it.data)
                is Either.Failure -> it
            }
        }

    }
}