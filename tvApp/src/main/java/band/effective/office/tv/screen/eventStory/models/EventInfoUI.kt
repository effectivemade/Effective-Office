package band.effective.office.tv.screen.eventStory.models

import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.screen.sport.model.SportUserUi
import band.effective.office.tv.screen.duolingo.model.DuolingoUserUI
import band.effective.office.tv.screen.eventStory.KeySortDuolingoUser
import band.effective.office.tv.screen.supernova.model.SupernovaUserUi

sealed class EmployeeInfoUI(
    val name: String,
    val photoUrl: String,
    open val isIntern: Boolean,
    ) : StoryModel(StoryType.Employee)

data class BirthdayUI(
    val employeeName: String,
    val employeePhotoUrl: String,
    override val isIntern: Boolean,
    ) : EmployeeInfoUI(employeeName, employeePhotoUrl, isIntern)

data class AnnualAnniversaryUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
    override val isIntern: Boolean,
    val yearsInCompany: Int
) : EmployeeInfoUI(employeeName, employeePhotoUrl, isIntern)

data class MonthAnniversaryUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
    override val isIntern: Boolean,
    val yearsInCompany: Int,
    val monthsInCompany: Int
) : EmployeeInfoUI(employeeName, employeePhotoUrl, isIntern)


data class NewEmployeeUI(
    private val employeeName: String,
    private val employeePhotoUrl: String,
) : EmployeeInfoUI(employeeName, employeePhotoUrl, isIntern = false)

data class DuolingoUserInfo(
    val keySort: KeySortDuolingoUser,
    val users: List<DuolingoUserUI>
): StoryModel(StoryType.Duolingo)

data class SportUserInfo(
    val users: List<SportUserUi>
) : StoryModel(StoryType.Sport)

data class MessageInfo(
    val message: BotMessage
): StoryModel(StoryType.Message)

data class SupernovaUserInfo(
    val users: List<SupernovaUserUi>
) : StoryModel(StoryType.Supernova)

enum class StoryType {
    Duolingo, Employee, Message, Sport, Supernova
}

abstract class StoryModel(val storyType: StoryType)
