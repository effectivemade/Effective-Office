package office.effective.features.notifications.routes

import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.notifications.FcmNotificationSender
import office.effective.common.notifications.INotificationSender
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.notifications.routes.swagger.receiveNotification
import org.koin.core.context.GlobalContext
import org.slf4j.LoggerFactory

/**
 * Route for Google calendar push notifications
 */
fun Route.calendarNotificationsRouting() {
    route("/notifications") {
        val messageSender: INotificationSender = GlobalContext.get().get()

        post(SwaggerDocument.receiveNotification()) {
            val logger = LoggerFactory.getLogger(FcmNotificationSender::class.java)
            logger.info("[calendarNotificationsRouting] received push notification: {}", call.receive<String>())
            // Calendar's resource id, https://developers.google.com/calendar/api/guides/push#headers
            val resourceId = call.request.header("X-Goog-Resource-ID")
            if (resourceId == null) {
                logger.warn("[calendarNotificationsRouting] resource id header was null")
                return@post call.respond(HttpStatusCode.OK)
            }
            messageSender.sendUpdateContentMessages("booking", resourceId)
            call.respond(HttpStatusCode.OK)
        }
    }
}
