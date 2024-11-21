package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event
import band.effective.office.workTogether.Teammate
import band.effective.office.workTogether.WorkTogether
import java.time.LocalDate

class NewEmployeeRepository(workTogether: WorkTogether) : EmployeeEventRepository(workTogether) {
    override fun check(teammate: Teammate): Boolean = teammate.startDate == LocalDate.now()
    override fun Teammate.toEvent(): Event = Event.NewEmployee(employeeName = name, employeePhotoUrl = photo)
}