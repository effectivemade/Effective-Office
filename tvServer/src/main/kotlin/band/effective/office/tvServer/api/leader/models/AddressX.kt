package band.effective.office.tvServer.api.leader.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class AddressX(
    @JsonNames("city_id")
    val cityId: Int?,
    @JsonNames("country_id")
    val countryId: Int?,
    @JsonNames("geo_point")
    val geoPoint: String?,
    @JsonNames("geo_point_zoom")
    val geoPointZoom: String?,
    val house: String?,
    val id: Int?,
    @JsonNames("region_id")
    val regionId: Int?,
    val street: String?,
    val title: String?,
)