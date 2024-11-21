package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event
import band.effective.office.workTogether.Teammate
import band.effective.office.workTogether.WorkTogether
import java.time.LocalDate
import java.time.LocalDateTime

class MonthAnniversaryRepository(workTogether: WorkTogether) : EmployeeEventRepository(workTogether) {
    override fun check(teammate: Teammate): Boolean {
        val now = LocalDate.now()
        val start = teammate.startDate
        return now.minusMonths(3) == start
    }

    override fun Teammate.toEvent(): Event =
        Event.MonthAnniversary(
            employeeName = name,
            employeePhotoUrl = photo,
            yearsInCompany = LocalDateTime.now().year - startDate.year,
            monthsInCompany = LocalDateTime.now().monthValue - startDate.monthValue
        )
}