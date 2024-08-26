package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.domain.model.notion.EmploymentType
import band.effective.office.tv.repository.clockify.ClockifyRepository
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.tv.repository.workTogether.Teammate
import band.effective.office.tv.repository.workTogether.WorkTogether
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import javax.inject.Inject
import kotlinx.coroutines.flow.flow as flow

// this use case combine data from many data sources for EvenStory screen
class EventStoryDataCombinerUseCase @Inject constructor(
    private val duolingoRepository: DuolingoRepository,
    private val clockifyRepository: ClockifyRepository,
    private val workTogether: WorkTogether
) {

    private fun Teammate.toEmployeeInfoEntity() =
        SimpleDateFormat("yyyy-MM-dd").let { formater ->
            EmployeeInfoEntity(
                firstName = name,
                startDate = formater.format(startDate.time),
                nextBirthdayDate = formater.format(nextBDay.time),
                photoUrl = photo,
                isIntern = employment == EmploymentType.Intern.value,
            )
        }

    fun getNotionDataForStories() = flow {
        val response = try {
            val activeEmployees = workTogether.getAll()
                .filter {
                    it.employment in setOf(EmploymentType.Band.value, EmploymentType.Intern.value)
                            && it.status == "Active"
                }
            Either.Success(activeEmployees)
        } catch (t: Throwable) {
            Either.Failure(
                t.message ?: "Error in EventStoryDataCombinerUseCase.getNotionDataForStories"
            )
        }
        emit(response)
    }.map {
        when (it) {
            is Either.Failure -> it
            is Either.Success -> {
                Either.Success(it.data.map { it.toEmployeeInfoEntity() })
            }
        }
    }

    suspend fun getClockifyDataForStories() = clockifyRepository.getTimeEntries()

    suspend fun getDuolingoDataForStories() = duolingoRepository.getUsers()

    fun getMessagesFromMattermost() = MessageQueue.secondQueue.toListOfEmployeeInfo()

    private fun MessageQueue.toListOfEmployeeInfo(): List<MessageInfo> =
        this.queue.value.queue.map { MessageInfo(it) }
}
