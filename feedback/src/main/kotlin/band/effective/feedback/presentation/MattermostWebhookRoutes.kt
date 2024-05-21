package band.effective.feedback.presentation

import band.effective.feedback.domain.model.Feedback
import band.effective.feedback.domain.model.FeedbackStorage
import band.effective.feedback.domain.useCase.SaveFeedbackUseCase
import band.effective.feedback.domain.useCase.StartFeedbackLoopUseCase
import band.effective.feedback.presentation.model.Reaction
import band.effective.feedback.presentation.model.WebHookDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import java.net.URI

fun decodeDto(body: String) = try {
    Json { ignoreUnknownKeys = true }.decodeFromString(WebHookDto.serializer(), body)
} catch (e: Throwable) {
    println("start: ${e.stackTraceToString()}")
    body.split("&").associate {
        val key = it.substringBefore("=")
        val value = it.replace(key, "").drop(1)
        key to value
    }.run {
        WebHookDto(
            text = getOrDefault("text", "").replace("%40", "@"),
            user_name = getOrDefault("user_name", "")
        ).apply {
            println("map: $this")
        }
    }
}

fun Route.mattermostWebhookRoutes() {
    val startFeedbackLoopUseCase: StartFeedbackLoopUseCase by inject()
    val saveFeedbackUseCase: SaveFeedbackUseCase by inject()
    val reactor: Reactor by inject()

    route("/mattermost/feedback") {
        post("/start") {
            val webHookDto = decodeDto(call.receiveText())
            val requester = webHookDto.user_name
            val requested = webHookDto.text.split(" ").filter { it.firstOrNull() == '@' }.map { it.drop(1) }
            val store = when {
                webHookDto.text.contains("--ydb") -> FeedbackStorage.Ydb
                else -> FeedbackStorage.Mattermost
            }
            with(reactor) {
                startFeedbackLoopUseCase.start(
                    userRequester = requester,
                    usersRequested = requested,
                    feedbackStorage = store
                ).foldWithReaction(
                    dto = webHookDto,
                    onSuccess = { call.respond(HttpStatusCode.Created) },
                    onFailure = {
                        println(it.stackTraceToString())
                        call.respond(
                            status = HttpStatusCode.InternalServerError,
                            message = it.stackTraceToString()
                        )
                    }
                )
            }
        }
        post("send") {
            val webHookDto = decodeDto(call.receiveText())
            val requester = webHookDto.text.split(" ").firstOrNull { it.firstOrNull() == '@' }?.drop(1)
            if (requester == null) {
                reactor.makeReaction(dto = webHookDto, reaction = Reaction.FAIL)
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Requester not found"
                )
                return@post
            }
            with(reactor) {
                saveFeedbackUseCase.save(webHookDto.getFeedback(), requester).foldWithReaction(
                    dto = webHookDto,
                    onSuccess = { call.respond(HttpStatusCode.Created) },
                    onFailure = {
                        println(it.stackTraceToString())
                        call.respond(
                            status = HttpStatusCode.InternalServerError,
                            message = it.stackTraceToString()
                        )
                    }
                )
            }
        }
    }
}

private fun WebHookDto.getFeedback() = Feedback(name = user_name, text = text)