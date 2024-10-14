package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.R
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.utils.StringResource
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
                t.message?.let { StringResource.DynamicResource(it) } ?:
                StringResource.AndroidResource(id = R.string.error_notion_usecase)
            )
        }
        emit(response)
    }
}