package band.effective.office.tv.domain.model.leaderId

import java.util.GregorianCalendar

data class LeaderIdEventInfo(
    val id: Int,
    val name: String,
    val startDateTime: GregorianCalendar,
    val finishDateTime: GregorianCalendar,
    val isOnline: Boolean,
    val photoUrl: String?,
    val organizer: String? = null,
    val speakers: List<String>? = null,
    val endRegDate: GregorianCalendar? = null
) {
    constructor(errorMessage: String) : this(
        id = -1,
        name = errorMessage,
        startDateTime = GregorianCalendar(),
        finishDateTime = GregorianCalendar(),
        isOnline = false,
        photoUrl = null
    )
}