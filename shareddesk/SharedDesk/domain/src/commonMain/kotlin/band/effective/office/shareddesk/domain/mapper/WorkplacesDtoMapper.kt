package band.effective.office.shareddesk.domain.mapper

import band.effective.office.shareddesk.data.models.WorkplaceDto
import band.effective.office.shareddesk.domain.models.Workplace

internal class WorkplacesDtoMapper {

    fun map(domain: Workplace): WorkplaceDto {
        return WorkplaceDto(
            id = domain.id,
            isBusy = domain.isBusy,
        )
    }
}