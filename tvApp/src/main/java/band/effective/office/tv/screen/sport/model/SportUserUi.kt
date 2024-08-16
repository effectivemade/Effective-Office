package band.effective.office.tv.screen.sport.model

import band.effective.office.tv.domain.model.clockify.ClockifyUser

data class SportUserUi(
    val name: String,
    val photo: String,
    val totalSeconds: Int
)

fun ClockifyUser.toUi() =
    SportUserUi(
        name = this.name,
        photo = this.photo,
        totalSeconds = this.totalSeconds
    )

fun List<ClockifyUser>.toUi() = map { it.toUi() }

fun <T> List<T>.toTwoColumns(countUsersToShow: Int): List<List<T>> {
    val rows = countUsersToShow / 2
    return if (this.size > rows) {
        listOf(this.subList(0, rows), this.subList(rows, this.size))
    } else listOf(this)
}
