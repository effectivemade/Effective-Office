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
import org.koin.ktor.ext.inject

fun Route.mattermostWebhookRoutes() {
    val startFeedbackLoopUseCase: StartFeedbackLoopUseCase by inject()
    val saveFeedbackUseCase: SaveFeedbackUseCase by inject()
    val reactor: Reactor by inject()

    route("/mattermost/feedback") {
        post("/start") {
            val webHookDto = call.receive<WebHookDto>()
            val requester = webHookDto.user_name
            val requested = webHookDto.text.split(" ").filter { it.firstOrNull() == '@' }.map { it.drop(1) }
            with(reactor) {
                startFeedbackLoopUseCase.start(requester, requested, FeedbackStorage.Ydb).foldWithReaction(
                    postId = webHookDto.post_id,
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
            val webHookDto = call.receive<WebHookDto>()
            val requester = webHookDto.text.split(" ").firstOrNull { it.firstOrNull() == '@' }?.drop(1)
            if (requester == null) {
                reactor.makeReaction(postId = webHookDto.post_id, reaction = Reaction.FAIL)
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Requester not found"
                )
                return@post
            }
            with(reactor) {
                saveFeedbackUseCase.save(webHookDto.getFeedback(), requester).foldWithReaction(
                    postId = webHookDto.post_id,
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