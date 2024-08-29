package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.workTogether.WorkTogether
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WorkTogetherUseCase @Inject constructor(
    private val workTogether: WorkTogether
) {
    fun getNotionDataForStories() = flow {
        val response = try {
            val activeEmployees = workTogether.getActive()
            Either.Success(activeEmployees)
        } catch (t: Throwable) {
            Either.Failure(
                t.message ?: "Error in EventStoryDataCombinerUseCase.getNotionDataForStories"
            )
        }
        emit(response)
    }
}