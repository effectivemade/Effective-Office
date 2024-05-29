package band.effective.office.tvServer.route.event

import band.effective.office.tvServer.route.event.EventDto.Companion.toDto
import band.effective.office.tvServer.service.event.EventService
import band.effective.office.tvServer.utils.authenticateWithApiKey
import band.effective.office.tvServer.utils.savePipeline
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.notion() {
    val eventService: EventService by inject()
    authenticateWithApiKey {
        get("/event") {
            savePipeline {
                call.respond(eventService.getEvents().map { it.toDto() })
            }
        }
    }

}