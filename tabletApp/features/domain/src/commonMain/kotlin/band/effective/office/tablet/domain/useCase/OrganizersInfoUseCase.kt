package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.network.repository.OrganizerRepository

/**Use case for get organizers list*/
class OrganizersInfoUseCase(private val repository: OrganizerRepository) {
    suspend operator fun invoke() = repository.getOrganizersList()
}