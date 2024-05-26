package band.effective.feedback.presentation

import band.effective.feedback.domain.model.Feedback
import band.effective.feedback.domain.model.FeedbackStorage
import band.effective.feedback.domain.repository.FeedbackRequestersRepository
import band.effective.feedback.domain.useCase.SaveFeedbackUseCase
import band.effective.feedback.domain.useCase.StartFeedbackLoopUseCase
import band.effective.feedback.mattermostModal.MattermostWindowRequester.showWindow
import band.effective.feedback.presentation.model.*
import band.effective.feedback.utils.Env
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.mattermostWebhookRoutes() {
    val startFeedbackLoopUseCase: StartFeedbackLoopUseCase by inject()
    val saveFeedbackUseCase: SaveFeedbackUseCase by inject()
    val reactor: Reactor by inject()
    val feedbackRequestersRepository: FeedbackRequestersRepository by inject()

    route("/mattermost/feedback") {
        mattermostRoute("/start") {
            onWebhook { webHookDto ->
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
                        onSuccess = { HttpStatusCode.Created },
                        onFailure = {
                            println(it.stackTraceToString())
                            HttpStatusCode.InternalServerError
                        }
                    )
                }
            }

            onCommand { map ->
                val user = map["user_name"]
                val users = map["text"]?.run {
                    replace("+", " ")
                        .replace("%40", "@")
                        .split(" ")
                        .filter { it.firstOrNull() == '@' }
                        .map { drop(1) }
                }
                if (users != null && user != null) {
                    respondMessage {
                        text = "Выбери куда сохранять feedback"
                        data = Json.encodeToString(StartData.serializer(), StartData(user, users))
                        addAction("notion", "${Env.myUrl}/mattermost/feedback/start/notion/click")
                        addAction("mattermost", "${Env.myUrl}/mattermost/feedback/start/mattermost")
                    }
                } else {
                    respondMessage { text = "ultra fail" }
                }
            }
        }

        post("/start/notion/click") {
            val request = call.receive<InteractiveMassageRequest>()
            showWindow(
                token = "",
                trigger_id = request.trigger_id,
                url = "${Env.myUrl}/mattermost/feedback/start/notion/submit"
            ) {
                println(request.context.data)
                state = request.context.data
                text(
                    display_name = "Notion",
                    name = "notion_url",
                    subtype = "",
                    placeholder = "",
                    help_text = "Enter notion url"
                )
            }
            call.respond(HttpStatusCode.OK)
        }

        post("/start/notion/submit") {
            val request = call.receive<InterectiveMessageSubmit>()
            val data = Json.decodeFromString<StartData>(request.state)
            println(data)
            val respond = startFeedbackLoopUseCase.start(
                data.requester,
                data.requested.map { it.replace("40", "") },
                FeedbackStorage.Notion(request.submission.notion_url)
            ).fold(
                onSuccess = {
                    respondMessage { text = "success" }
                },
                onFailure = {
                    respondMessage { text = "fail" }
                }
            )
            println("check")
            println(respond)
            call.respond(HttpStatusCode.OK, respond)
        }
        post("/start/mattermost") {
            try {
                val request = call.receive<InteractiveMassageRequest>()
                val data = Json.decodeFromString<StartData>(request.context.data)
                val respond = startFeedbackLoopUseCase.start(
                    data.requester,
                    data.requested.map { it.replace("40", "") },
                    FeedbackStorage.Mattermost
                ).fold(
                    onSuccess = {
                        respondMessage { text = "success" }
                    },
                    onFailure = {
                        respondMessage { text = "fail" }
                    }
                )
                call.respond(HttpStatusCode.OK, respond)
            } catch (e: Throwable) {
                println(e.stackTraceToString())
                call.respond(HttpStatusCode.OK)
            }
        }

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
}

private fun WebHookDto.getFeedback() = Feedback(name = user_name, text = text)