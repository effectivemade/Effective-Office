package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event
import band.effective.office.workTogether.Teammate
import band.effective.office.workTogether.WorkTogether
import java.time.LocalDateTime

class AnnualAnniversaryRepository(workTogether: WorkTogether) : EmployeeEventRepository(workTogether) {
    override fun check(teammate: Teammate): Boolean {
        val now = LocalDateTime.now()
        val start = teammate.startDate
        return now.monthValue == start.monthValue && now.dayOfMonth == start.dayOfMonth && now.year != start.year
    }

    override fun Teammate.toEvent(): Event =
        Event.AnnualAnniversary(name, photo, LocalDateTime.now().year - startDate.year)
}