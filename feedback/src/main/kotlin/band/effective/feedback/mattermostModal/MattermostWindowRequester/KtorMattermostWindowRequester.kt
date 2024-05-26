package band.effective.feedback.mattermostModal.MattermostWindowRequester

import band.effective.feedback.mattermostModal.MattermostWindow
import band.effective.feedback.mattermostModal.MattermostWindowBuilder
import band.effective.feedback.utils.Env
import band.effective.feedback.utils.KtorClientBuilder
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorMattermostWindowRequester(
    private val client: HttpClient,
    private val baseUrl: String,
    override val trigger_id: String,
    override val url: String
) : MattermostWindowRequester() {
    override suspend fun send(request: WindowRequest) {
        client.post("${baseUrl}/api/v4/actions/dialogs/open") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }
}

suspend fun showWindow(
    token: String,
    client: HttpClient = KtorClientBuilder { setToken(token) },
    baseUrl: String = Env.mattermostServer,
    trigger_id: String,
    url: String,
    builder: MattermostWindowBuilder.() -> Unit
) {
    KtorMattermostWindowRequester(client, baseUrl, trigger_id, url).send(MattermostWindow(builder))
}