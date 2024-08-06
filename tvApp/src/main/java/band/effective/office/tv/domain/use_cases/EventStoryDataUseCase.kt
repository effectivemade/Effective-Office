package band.effective.office.tv.domain.use_cases

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.clockify.ClockifyUser
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.domain.model.notion.EmploymentType
import band.effective.office.tv.domain.model.notion.processEmployeeInfo
import band.effective.office.tv.repository.clockify.ClockifyRepository
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.tv.repository.workTogether.Teammate
import band.effective.office.tv.repository.workTogether.WorkTogether
import band.effective.office.tv.screen.sport.model.toUi
import band.effective.office.tv.screen.duolingo.model.toUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import band.effective.office.tv.screen.eventStory.models.SportUserInfo
import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.screen.sport.model.toColumns
import kotlinx.coroutines.flow.combine
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
    private val countShowUsers = 10

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

    private fun getNotionDataForStories() = flow {
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

    suspend fun getAllDataForStories() =
        combine(
            getNotionDataForStories(),
            duolingoRepository.getUsers(),
            clockifyRepository.getTimeEntries()
        ) { employeeInfoEntities, usersDuolingo, clockifyUsers ->
            var error = ""
            val duolingoInfo = when (usersDuolingo) {
                is Either.Success -> {
                    setDuolingoDataForScreens(usersDuolingo.data)
                }

                is Either.Failure -> {
                    error = usersDuolingo.error
                    emptyList()
                }
            }
            val notionInfo = when (employeeInfoEntities) {
                is Either.Success -> {
                    employeeInfoEntities.data.processEmployeeInfo()
                }

                is Either.Failure -> {
                    error = employeeInfoEntities.error
                    emptyList()
                }
            }

            val clockifyInfo = when (clockifyUsers) {
                is Either.Success -> {
                    setClockifyDataForScreens(clockifyUsers.data)
                }

                is Either.Failure -> {
                    error = clockifyUsers.error
                    emptyList()
                }
            }

            val messagesFromMattermost = MessageQueue.secondQueue.toListOfEmployeeInfo()

            if (notionInfo.isEmpty() && duolingoInfo.isEmpty() && clockifyInfo.isEmpty()) return@combine Either.Failure(
                error
            )
            else Either.Success(notionInfo + duolingoInfo + clockifyInfo + messagesFromMattermost)
        }

    private fun MessageQueue.toListOfEmployeeInfo(): List<MessageInfo> =
        this.queue.value.queue.map { MessageInfo(it) }

    private fun <T> List<T>.subListForScreen() =
        subList(
            fromIndex = 0,
            toIndex = if (size <= countShowUsers) size else countShowUsers + 1
        )

    private fun setDuolingoDataForScreens(duolingoUsers: List<DuolingoUser>) =
        run {
            val userXpSort = DuolingoUserInfo(
                users = duolingoUsers
                    .sortedByDescending { it.totalXp }
                    .subListForScreen().toUI(),
                keySort = KeySortDuolingoUser.Xp
            ) as StoryModel
            val userStreakSort = DuolingoUserInfo(
                users = duolingoUsers
                    .sortedByDescending { it.streakDay }
                    .subListForScreen().toUI()
                    .filter { it.streakDay != 0 },
                keySort = KeySortDuolingoUser.Streak
            ) as StoryModel
            listOf(userXpSort, userStreakSort)
        }

    private fun setClockifyDataForScreens(clockifyUsers: List<ClockifyUser>) =
        listOf(SportUserInfo(
            users = clockifyUsers
                .subListForScreen()
                .toUi()
                .sortedByDescending { it.totalTime }
                .toColumns(countShowUsers)
        ) as StoryModel)
}
