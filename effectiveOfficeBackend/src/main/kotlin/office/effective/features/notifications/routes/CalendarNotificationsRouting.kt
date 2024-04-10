package office.effective.features.notifications.routes

import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.notifications.FcmNotificationSender
import office.effective.common.notifications.INotificationSender
import org.koin.core.context.GlobalContext
import org.slf4j.LoggerFactory

/**
 * Route for Google calendar push notifications
 */
fun Route.calendarNotificationsRouting() {
    route("/notifications") {
        val messageSender: INotificationSender = GlobalContext.get().get()

        post() {
            val logger = LoggerFactory.getLogger(FcmNotificationSender::class.java)
            logger.info("[calendarNotificationsRouting] received push notification: \n{}", call.receive<String>())
            messageSender.sendEmptyMessage("booking")
            call.respond(HttpStatusCode.OK)
        }
    }
}
