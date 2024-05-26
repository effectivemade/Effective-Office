package band.effective.feedback.mattermostModal.MattermostWindowRequester

import band.effective.feedback.mattermostModal.MattermostWindow
import kotlinx.serialization.Serializable

@Serializable
data class WindowRequest(
    val trigger_id: String,
    val url: String,
    val dialog: MattermostWindow
)