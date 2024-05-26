package band.effective.feedback.mattermostModal.MattermostWindowRequester

import band.effective.feedback.mattermostModal.MattermostWindow

abstract class MattermostWindowRequester {
    abstract val trigger_id: String
    abstract val url: String

    protected abstract suspend fun send(request: WindowRequest)

    suspend fun send(window: MattermostWindow) = send(WindowRequest(trigger_id, url, window))
}