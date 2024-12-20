package band.effective.office.tv.domain.model.clockify

import band.effective.office.tv.domain.model.StoryDomainModel
import band.effective.office.tv.network.clockify.models.responce.ClockifyResponse

data class ClockifyUser(
    val name: String,
    val email: String,
    val totalSeconds: Int
) : StoryDomainModel()

fun ClockifyResponse.toDomainList(): List<ClockifyUser> =
    timeEntries
        .groupBy { it.userEmail }
        .map { (email, timeEntries) ->
            ClockifyUser(
                email = email,
                name = timeEntries[0].userName,
                totalSeconds = timeEntries.sumOf { it.timeInterval.duration }
            )
        }
