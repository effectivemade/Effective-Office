package band.effective.office.tvServer.route.leader

import band.effective.office.tvServer.model.LeaderIdEventInfo
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Serializable
data class LeaderIdDto(
    val id: Int,
    val name: String,
    val startDateTime: String,
    val finishDateTime: String,
    val isOnline: Boolean,
    val photoUrl: String?,
    val organizer: String?,
    val speakers: List<String>?,
    val endRegDate: String?
) {
    companion object {
        fun LeaderIdEventInfo.toDto() = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").let { formatter ->
            LeaderIdDto(
                id = id,
                name = name,
                startDateTime = startDateTime.format(formatter),
                finishDateTime = finishDateTime.format(formatter),
                isOnline = isOnline,
                photoUrl = photoUrl,
                organizer = organizer,
                speakers = speakers,
                endRegDate = endRegDate?.format(formatter)
            )
        }
    }
}
