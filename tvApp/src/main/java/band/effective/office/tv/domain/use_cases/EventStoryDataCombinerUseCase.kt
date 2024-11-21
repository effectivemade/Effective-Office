package band.effective.office.tv.domain.use_cases

import android.annotation.SuppressLint
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.StoryDomainModel
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.domain.model.notion.EmploymentType
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import band.effective.office.tv.utils.StringResource
import band.effective.office.workTogether.Teammate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import java.time.format.DateTimeFormatter
import javax.inject.Inject

// this use case combine data from many data sources for EvenStory screen
class EventStoryDataCombinerUseCase @Inject constructor(
    val workTogetherUseCase: WorkTogetherUseCase,
    val duolingoDataUseCase: DuolingoDataUseCase,
    val clockifyDataUseCase: ClockifyDataUseCase,
    val supernovaDataUseCase: SupernovaDataUseCase
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getAllDataForStories() =
        workTogetherUseCase.getNotionDataForStories()
            .flatMapConcat { notionUsers ->
                when (notionUsers) {
                    is Either.Success -> {
                        combine(
                            duolingoDataUseCase.getDuolingoDataForStories(notionUsers.data),
                            clockifyDataUseCase.getClockifyDataForStories(),
                            supernovaDataUseCase.getSupernovaData()
                        ) { duolingoData, clockifyData, supernovaData ->
                            val combinedStoryModels: MutableList<StoryDomainModel> = mutableListOf()
                            lateinit var error: StringResource

                            combinedStoryModels += notionUsers.data.map { it.toEmployeeInfoEntity() }

                            when (duolingoData) {
                                is Either.Success -> combinedStoryModels += duolingoData.data
                                is Either.Failure -> error = StringResource.DynamicResource(duolingoData.error)
                            }
                            when (clockifyData) {
                                is Either.Success -> combinedStoryModels += clockifyData.data
                                is Either.Failure -> error = StringResource.DynamicResource(clockifyData.error)
                            }
                            when (supernovaData) {
                                is Either.Success -> combinedStoryModels += supernovaData.data
                                is Either.Failure -> error = supernovaData.error
                            }

                            if (combinedStoryModels.isEmpty()) return@combine Either.Failure(error)
                            else Either.Success(combinedStoryModels)
                        }
                    }
                    is Either.Failure -> {
                        flowOf(Either.Failure(notionUsers.error))
                    }
                }
            }

    private fun MessageQueue.toListOfEmployeeInfo(): List<MessageInfo> =
        this.queue.value.queue.map { MessageInfo(it) }

    @SuppressLint("NewApi")
    private fun Teammate.toEmployeeInfoEntity() =
        DateTimeFormatter.ofPattern("yyyy-MM-dd").let { formater ->
            EmployeeInfoEntity(
                id = id,
                firstName = name,
                workEmail = workEmail ?: "",
                startDate = formater.format(startDate),
                nextBirthdayDate = formater.format(nextBDay),
                photoUrl = photo,
                isIntern = employment == EmploymentType.Intern.value,
            )
        }
}
