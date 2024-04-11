package band.effective.office.tvServer.repository.event

import band.effective.office.tvServer.model.Event

class CombineEventRepository(private val eventRepositoryes: List<EventRepository>) : EventRepository {
    override fun getEvents(): List<Event> = eventRepositoryes.map { it.getEvents() }.flatten()
}