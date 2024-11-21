package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Props(
    @Json(name = "disable_group_highlight")
    val disableGroupHighlight: Boolean?
)