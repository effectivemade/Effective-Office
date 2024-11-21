package band.effective.office.tablet.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Booking(
    val nameRoom: String,
    val roomId: String,
    val eventInfo: EventInfo
) : Parcelable {
    companion object {
        val default = Booking(nameRoom = "", roomId = "", eventInfo = EventInfo.emptyEvent)
    }
}