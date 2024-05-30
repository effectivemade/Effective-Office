package band.effective.feedback.presentation.utils

import band.effective.feedback.presentation.model.RespondMessage
import band.effective.feedback.presentation.model.WebHookDto
import band.effective.feedback.presentation.model.respondMessage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

class MattermostRoute(
    private val onWebhook: suspend (WebHookDto) -> HttpStatusCode,
    private val onCommand: suspend (Map<String, String>) -> RespondMessage
) {

    private val json = Json { ignoreUnknownKeys = true }

    fun Route.receiveRequest(route: String) =
        post(route) {
            val route = this@MattermostRoute
            val body = call.receiveText()
            runCatching {
                val dto = json.decodeFromString(WebHookDto.serializer(), body)
                call.respond(route.onWebhook(dto))
            }.recoverCatching {
                println("not web hook: $body")
                println("err: $body")
                val dto = body.toMap()
                call.respond(HttpStatusCode.OK, route.onCommand(dto))
            }.getOrElse {
                println("not command: ${it.stackTraceToString()}")
                println("err: $body")
                call.respond(HttpStatusCode.OK)
            }
        }

    class MattermostRouteBuilder() {
        var parent: Route? = null
        var route: String = ""
        var onWebhookCallback: suspend (WebHookDto) -> HttpStatusCode = { HttpStatusCode.NotFound }
        var onCommandCallback: suspend (Map<String, String>) -> RespondMessage =
            { respondMessage { text = "not found" } }

        fun onWebhook(callback: suspend (WebHookDto) -> HttpStatusCode) {
            onWebhookCallback = callback
        }

        fun onCommand(callback: suspend (Map<String, String>) -> RespondMessage) {
            onCommandCallback = callback
        }

        fun build() = MattermostRoute(onWebhookCallback, onCommandCallback)
    }

    companion object {
        private fun String.toMap() =
            split("&").associate {
                val key = it.substringBefore("=")
                val value = it.replace(key, "").drop(1)
                key to value
            }

        fun Route.mattermostRoute(route: String, builder: MattermostRouteBuilder.() -> Unit): Route =
            MattermostRouteBuilder().apply {
                parent = this@mattermostRoute
                this.route = route
                builder()
            }.build().run { receiveRequest(route) }
    }
}