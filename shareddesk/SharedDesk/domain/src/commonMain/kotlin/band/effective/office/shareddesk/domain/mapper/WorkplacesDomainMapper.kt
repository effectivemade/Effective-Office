package band.effective.office.shareddesk.domain.mapper

import band.effective.office.shareddesk.data.models.WorkplaceDto
import band.effective.office.shareddesk.domain.models.Workplace

internal class WorkplacesDomainMapper {

    fun map(dto: WorkplaceDto): Workplace {
        return Workplace(
            id = dto.id,
            isBusy = dto.isBusy,
        )
    }
}
