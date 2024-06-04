package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.impl.BufferedRoomRepository

class AddCachedEventUseCase(private val bufferedRoomRepository: BufferedRoomRepository) {
    suspend operator fun invoke(roomName: String, eventInfo: EventInfo) {
        bufferedRoomRepository.addCachedEvent(roomName, eventInfo)
    }
}