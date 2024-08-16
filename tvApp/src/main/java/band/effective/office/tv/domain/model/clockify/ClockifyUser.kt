package band.effective.office.tv.domain.model.clockify

import band.effective.office.tv.network.clockify.models.responce.ClockifyResponse
import band.effective.office.tv.repository.workTogether.Teammate

data class ClockifyUser(
    val name: String,
    val photo: String,
    val totalSeconds: Int
)

fun ClockifyResponse.toDomainList(users: List<Teammate>): List<ClockifyUser> =
    this.timeEntries
        .groupBy { it.userEmail }
        .map { (email, timeEntries) ->
            val notionUser = users.find { it.workEmail == email }
            ClockifyUser(
                name = notionUser?.name?.split(' ')?.get(0) ?: email,
                photo = notionUser?.photo ?: "",
                totalSeconds = timeEntries.sumOf {it.timeInterval.duration}
            )
        }
