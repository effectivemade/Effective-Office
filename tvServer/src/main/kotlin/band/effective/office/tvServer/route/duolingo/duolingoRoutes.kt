package band.effective.office.tvServer.route.duolingo

import band.effective.office.tvServer.service.duolingo.DuolingoService
import band.effective.office.tvServer.utils.authenticateWithApiKey
import band.effective.office.tvServer.utils.savePipeline
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.duolingo() {
    val duolingoService: DuolingoService by inject()
    authenticateWithApiKey {
        get("/duolingo") {
            savePipeline {
                call.respond(duolingoService.getUsers())
            }
        }
    }
}