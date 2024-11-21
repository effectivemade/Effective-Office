package band.effective.office.tv.network.duolingo.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LiveOpsFeature(
    val endTimestamp: Int,
    val startTimestamp: Int,
    val type: String
)