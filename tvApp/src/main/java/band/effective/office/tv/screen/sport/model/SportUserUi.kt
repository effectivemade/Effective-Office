package band.effective.office.tv.screen.sport.model

import band.effective.office.tv.domain.model.clockify.ClockifyUser

data class SportUserUi(
    val name: String,
    val photo: String,
    val totalTime: Int
)

fun ClockifyUser.toUi() =
    SportUserUi(
        name = this.name,
        photo = this.photo,
        totalTime = this.totalTime
    )

fun List<ClockifyUser>.toUi() = map { it.toUi() }