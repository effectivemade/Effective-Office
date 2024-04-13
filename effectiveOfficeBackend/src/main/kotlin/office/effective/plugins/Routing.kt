package office.effective.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.user.routes.userRouting
import office.effective.features.booking.routes.bookingRouting
import office.effective.features.booking.routes.bookingRoutingV1
import office.effective.features.notifications.routes.calendarNotificationsRouting
import office.effective.features.workspace.routes.workspaceRouting

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        workspaceRouting()
        userRouting()
        bookingRouting()
        bookingRoutingV1()
        calendarNotificationsRouting()
    }

}
