package band.effective.office.tv.network.leader.models.eventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventInfoResponse(
    val data: Data
)