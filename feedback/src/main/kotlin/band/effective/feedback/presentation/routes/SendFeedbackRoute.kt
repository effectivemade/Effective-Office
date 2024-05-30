package band.effective.feedback.presentation.routes

import band.effective.feedback.domain.model.Feedback
import band.effective.feedback.domain.repository.FeedbackRequestersRepository
import band.effective.feedback.domain.useCase.SaveFeedbackUseCase
import band.effective.feedback.mattermostModal.MattermostWindowRequester.showWindow
import band.effective.feedback.presentation.model.*
import band.effective.feedback.presentation.utils.MattermostRoute.Companion.mattermostRoute
import band.effective.feedback.presentation.utils.Reactor
import band.effective.feedback.utils.Env
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.sendFeedbackRoute() {
    val saveFeedbackUseCase: SaveFeedbackUseCase by inject()
    val reactor: Reactor by inject()
    val feedbackRequestersRepository: FeedbackRequestersRepository by inject()

    mattermostRoute("/send") {
        onWebhook { webHookDto ->
            val requester = webHookDto.text.split(" ").firstOrNull { it.firstOrNull() == '@' }?.drop(1)
            if (requester == null) {
                reactor.makeReaction(dto = webHookDto, reaction = Reaction.FAIL)
                return@onWebhook HttpStatusCode.BadRequest
            }
            with(reactor) {
                saveFeedbackUseCase.save(webHookDto.getFeedback(), requester).foldWithReaction(
                    dto = webHookDto,
                    onSuccess = { HttpStatusCode.Created },
                    onFailure = {
                        println(it.stackTraceToString())
                        HttpStatusCode.InternalServerError
                    }
                )
            }
        }

        onCommand { map ->
            val requesters = feedbackRequestersRepository.getRequesters(map["user_name"]!!)
            respondMessage {
                text = "Кому желаешь дать feedback"
                requesters.forEach { requester ->
                    addAction(requester, "${Env.myUrl}/mattermost/feedback/send/$requester")
                }
            }
        }
    }
    post("/send/{requester}") {
        val request = call.receive<InteractiveMassageRequest>()
        val requester = call.parameters["requester"]!!
        val requested = request.user_name
        showWindow(
            token = "",
            trigger_id = request.trigger_id,
            url = "${Env.myUrl}/mattermost/feedback/send/submit"
        ) {
            state = Json.encodeToString(SendData.serializer(), SendData(requester, requested))
            text(
                display_name = "Feedback",
                name = "feedback",
                placeholder = "enter your feedback"
            )
        }
        call.respond(HttpStatusCode.OK)
    }
    post("/send/submit") {
        val request = call.receive<InterectiveMessageSubmit>()
        val data = Json.decodeFromString<SendData>(request.state)
        val feedback = Feedback(data.requester, request.submission.feedback)
        saveFeedbackUseCase.save(feedback, data.requested).fold(
            onSuccess = { println("success") },
            onFailure = { println("fail") }
        )
    }
}

private fun WebHookDto.getFeedback() = Feedback(name = user_name, text = text)