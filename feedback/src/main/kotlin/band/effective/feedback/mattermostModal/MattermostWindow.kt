package band.effective.feedback.mattermostModal

import kotlinx.serialization.Serializable

@Serializable
class MattermostWindow(
    val title: String,
    val elements: List<Element.Text>,
    val introduction_text: String,
    val submit_label: String,
    val callback_id: String = "somecallbackid",
    val icon_url: String = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/Circle-icons-dev.svg/512px-Circle-icons-dev.svg.png",
    val state: String = ""
)