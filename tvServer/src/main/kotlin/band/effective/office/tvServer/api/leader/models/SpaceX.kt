package band.effective.office.tvServer.api.leader.models

import band.effective.office.tvServer.api.leader.models.searchEvent.PhotoXX
import band.effective.office.tvServer.api.leader.models.searchEvent.SocialNetworkX
import kotlinx.serialization.Serializable

@Serializable
data class SpaceX(
    val active: Boolean,
    val address: AddressX,
    val addressId: Int,
    val agenda: List<String>,
    val createdAt: String,
    val description: String,
    val email: String,
    val id: Int,
    val minimalPeriod: Int,
    val name: String,
    val phone: String,
    val photos: List<PhotoXX>,
    val restrictEventOwnerOfflineConfirm: Boolean,
    val scheduleOnRequest: Boolean,
    val socialNetworks: List<SocialNetworkX>,
    val square: String,
    val tags: List<String>,
    val type: String,
    val updatedAt: String
)