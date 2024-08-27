package band.effective.office.tv.screen.sport.model

import band.effective.office.tv.domain.model.clockify.ClockifyUser
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity

data class SportUserUi(
    val name: String,
    val photo: String,
    val totalSeconds: Int
)

fun List<ClockifyUser>.toUi(employees: List<EmployeeInfoEntity>) =
    mapNotNull { user ->
        employees.find { user.email == it.workEmail }?.let { employee ->
            SportUserUi(
                photo = employee.photoUrl,
                name = employee.firstName.split(' ')[0],
                totalSeconds = user.totalSeconds
            )
        }
    }

fun <T> List<T>.toTwoColumns(countUsersToShow: Int): List<List<T>> {
    val rows = countUsersToShow / 2
    return if (this.size > rows) {
        listOf(this.subList(0, rows), this.subList(rows, this.size))
    } else listOf(this)
}
