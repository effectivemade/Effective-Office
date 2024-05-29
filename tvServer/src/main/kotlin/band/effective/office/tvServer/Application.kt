package band.effective.office.tvServer

import band.effective.office.tvServer.di.di
import band.effective.office.tvServer.route.routes
import band.effective.office.tvServer.utils.contentNegotiation
import band.effective.office.tvServer.utils.logging
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val port = System.getenv("DEPLOYMENT_PORT")?.toInt() ?: 8080
    val host = System.getenv("DEPLOYMENT_HOST") ?: "0.0.0.0"
    embeddedServer(Netty, port = port, host = host, module = Application::module).start(wait = true)
}

fun Application.module() {
    di()
    contentNegotiation()
    logging()
    routes()
}




