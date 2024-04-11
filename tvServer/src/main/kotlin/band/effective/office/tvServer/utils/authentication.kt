package band.effective.office.tvServer.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticateWithApiKey(build: Route.() -> Route): Route {
    val apikey = System.getenv("OURKEY")
    val route = createChild(RootRouteSelector("/"))
    route.intercept(ApplicationCallPipeline.Setup) {
        val clientKey = call.request.header("X-EO-ApiKey") ?: call.respond(HttpStatusCode.Unauthorized)
        if (clientKey != apikey) call.respond(HttpStatusCode.Unauthorized)
    }
    route.build()
    return route
}