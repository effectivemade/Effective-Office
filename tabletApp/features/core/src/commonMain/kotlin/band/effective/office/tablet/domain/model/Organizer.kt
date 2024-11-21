package band.effective.office.tablet.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Organizer(val fullName: String, val id: String, val email: String?) : Parcelable {
    companion object {
        val default = Organizer(
            fullName = "", id = "", email = null
        )
    }
}

