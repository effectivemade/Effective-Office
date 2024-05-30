package band.effective.feedback.presentation.routes

import io.ktor.server.routing.*

fun Route.mattermostWebhookRoutes() {
    route("/mattermost/feedback") {
        startFeedbackRoutes()
        sendFeedbackRoute()
    }
}

