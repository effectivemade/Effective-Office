package band.effective.office.tvServer.model

import java.time.LocalDateTime

data class LeaderIdEventInfo(
    val id: Int,
    val name: String,
    val startDateTime: LocalDateTime,
    val finishDateTime: LocalDateTime,
    val isOnline: Boolean,
    val photoUrl: String?,
    val organizer: String?,
    val speakers: List<String>?,
    val endRegDate: LocalDateTime?
)