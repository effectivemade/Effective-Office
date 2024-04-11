package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event
import band.effective.office.workTogether.Teammate
import band.effective.office.workTogether.WorkTogether
import java.time.LocalDateTime

class BirthdayRepository(workTogether: WorkTogether) : EmployeeEventRepository(workTogether) {
    override fun check(teammate: Teammate): Boolean {
        val now = LocalDateTime.now()
        val bDay = teammate.nextBDay
        return now.monthValue == bDay.monthValue && now.dayOfMonth == bDay.dayOfMonth
    }

    override fun Teammate.toEvent(): Event = Event.Birthday(name = name, photoUrl = photo)
}