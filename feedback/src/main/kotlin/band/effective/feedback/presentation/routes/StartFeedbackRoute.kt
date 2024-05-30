package band.effective.feedback.presentation.routes

import band.effective.feedback.domain.model.FeedbackStorage
import band.effective.feedback.domain.useCase.StartFeedbackLoopUseCase
import band.effective.feedback.mattermostModal.MattermostWindowRequester.showWindow
import band.effective.feedback.presentation.model.InteractiveMassageRequest
import band.effective.feedback.presentation.model.InterectiveMessageSubmit
import band.effective.feedback.presentation.model.StartData
import band.effective.feedback.presentation.model.respondMessage
import band.effective.feedback.presentation.utils.MattermostRoute.Companion.mattermostRoute
import band.effective.feedback.utils.Env
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.startFeedbackRoutes() {
    val startFeedbackLoopUseCase: StartFeedbackLoopUseCase by inject()
    mattermostRoute("/start") {
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
}