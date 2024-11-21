package band.effective.office.tv.domain.model.leaderId

import band.effective.office.tv.network.leader.models.eventInfo.EventInfoResponse
import java.util.GregorianCalendar

fun EventInfoResponse.toLeaderIdEventInfo(): LeaderIdEventInfo = LeaderIdEventInfo(
    id = data.id,
    name = data.fullName,
    startDateTime = stringToDateTime(data.dateStart),
    finishDateTime = stringToDateTime(data.dateEnd),
    isOnline = data.status == "online",
    photoUrl = data.photo,
    organizer = data.organizers[0].name,
    speakers = data.speakers.map { "${it.user.firstName} ${it.user.lastName}" },
    endRegDate = stringToDateTime(data.registrationDateEnd)
)

fun stringToDateTime(str: String): GregorianCalendar {
    val date = str.substring(0, str.indexOf(" "))
    val time = str.replace(date + " ", "")
    val dateComponents = date.split("-")
    val timeComponents = time.split(":")
    return GregorianCalendar(
        dateComponents[0].toInt(),
        dateComponents[1].toInt() - 1,
        dateComponents[2].toInt(),
        timeComponents[0].toInt(),
        timeComponents[1].toInt()
    )
}