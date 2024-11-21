package band.effective.office.tv.network.leader.models

import band.effective.office.tv.network.leader.models.searchEvent.PhotoXX
import band.effective.office.tv.network.leader.models.searchEvent.SocialNetworkX
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpaceX(
    val active: Boolean,
    val address: AddressX,
    val addressId: Int,
    val agenda: List<String>,
    val createdAt: String,
    val description: String,
    val email: String,
    val id: Int,
    val kworkingState: Any?,
    val minimalPeriod: Int,
    val name: String,
    val phone: String,
    val phoneExtension: Any?,
    val photos: List<PhotoXX>,
    val rating: Any?,
    val restrictEventOwnerOfflineConfirm: Boolean,
    val scheduleOnRequest: Boolean,
    val socialNetworks: List<SocialNetworkX>,
    val square: String,
    val stat: StatX,
    val tags: List<String>,
    val type: String,
    val updatedAt: String
)