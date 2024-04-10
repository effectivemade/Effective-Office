package band.effective.office.tvServer.service.event

import band.effective.office.tvServer.repository.event.EventRepository

class EventService(private val eventRepository: EventRepository) {
    fun getEvents() = eventRepository.getEvents()
}