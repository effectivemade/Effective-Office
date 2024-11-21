package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event
import band.effective.office.workTogether.Teammate
import band.effective.office.workTogether.WorkTogether
import java.time.LocalDate

abstract class EmployeeEventRepository(private val workTogether: WorkTogether) : EventRepository {
    abstract fun check(teammate: Teammate): Boolean
    abstract fun Teammate.toEvent(): Event
    override fun getEvents(): List<Event> =
        workTogether.getActive()
            .filter { check(it) && it.haveCorrectTime() }
            .map { it.toEvent() }

    private fun Teammate.haveCorrectTime() = startDate != LocalDate.MIN && nextBDay != LocalDate.MIN
}