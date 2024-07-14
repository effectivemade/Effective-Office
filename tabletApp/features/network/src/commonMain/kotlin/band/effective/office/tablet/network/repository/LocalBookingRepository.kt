package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.RoomInfo

interface LocalBookingRepository : BookingRepository {
    fun updateRoomsInfo(roomsInfo: List<RoomInfo>)
}