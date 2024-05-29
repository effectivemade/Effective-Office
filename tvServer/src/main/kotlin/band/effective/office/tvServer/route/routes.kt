package band.effective.office.tvServer.route

import band.effective.office.tvServer.route.duolingo.duolingo
import band.effective.office.tvServer.route.event.notion
import band.effective.office.tvServer.route.leader.leaderId
import band.effective.office.tvServer.route.mattermost.mattermost
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.routes() {
    routing {
        leaderId()
        mattermost()
        notion()
        duolingo()
    }
}