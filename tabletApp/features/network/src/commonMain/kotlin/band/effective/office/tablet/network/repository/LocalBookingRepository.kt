package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.RoomInfo

interface LocalBookingRepository : BookingRepository {
    fun updateRoomsInfo(either: Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>)
}