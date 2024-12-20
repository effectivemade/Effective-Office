package band.effective.office.tablet.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomInfo(
    val name: String,
    val capacity: Int,
    val isHaveTv: Boolean,
    val socketCount: Int,
    val eventList: List<EventInfo>,
    val currentEvent: EventInfo?, //NOTE(Maksim Mishenko): currentEvent is null if room is free
    val id: String
) : Parcelable {
    companion object {
        val defaultValue =
            RoomInfo(
                name = "Default",
                capacity = 0,
                isHaveTv = false,
                socketCount = 0,
                eventList = listOf(),
                currentEvent = null,
                id = ""
            )
    }
}
