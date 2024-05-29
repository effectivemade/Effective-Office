package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.CancelRepository

/**Use case for cancel events*/
class CancelUseCase(private val cancelRepository: CancelRepository) {
    /**Cancel event
     * @param eventInfo canceled event
     * @return if cancel confirm then Either.Success else Either.Error with error code*/
    suspend operator fun invoke(eventInfo: EventInfo) = cancelRepository.cancelEvent(eventInfo)
}