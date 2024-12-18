package band.effective.office.shareddesk.interactivemap.presentation

import band.effective.office.shareddesk.interactivemap.presentation.models.WorkplaceUi

sealed interface OfficeMapEffect {
    data class ShowWorkplaceInfoModal(val workplaceUi: WorkplaceUi?): OfficeMapEffect
}