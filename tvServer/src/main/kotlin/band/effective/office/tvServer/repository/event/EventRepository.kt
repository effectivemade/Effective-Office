package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event

interface EventRepository {
    fun getEvents(): List<Event>
}