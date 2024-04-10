package band.effective.office.tvServer.route.leader

import band.effective.office.tvServer.route.leader.LeaderIdDto.Companion.toDto
import band.effective.office.tvServer.service.leaderId.LeaderIdService
import band.effective.office.tvServer.utils.savePipeline
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.inject

fun Route.leaderId() {
    val leaderIdService: LeaderIdService by inject()
    get("/leader") {
        savePipeline {
            val finishDate = call.request.queryParameters["finishDate"]
            val cityId = call.request.queryParameters["cityId"]
            val placeId = call.request.queryParameters["placeId"]
            call.respond(leaderIdService.getEvents(finishDate, cityId, placeId).toList().map { it.toDto() })
        }
    }
}