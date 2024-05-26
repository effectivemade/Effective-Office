package band.effective.feedback.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class RespondMessage(
    val response_type: String = "in_channel",
    val attachments: List<Attachment>
)

@Serializable
data class Attachment(
    val text: String,
    val actions: List<Action>
)

@Serializable
data class Action(
    val id: String = "id",
    val name: String,
    val integration: Integration
)

@Serializable
data class Integration(
    val url: String,
    val context: Context
)

@Serializable
data class Context(
    val data: String
)

fun getIntegration(url: String, data: String) = Integration(url, Context(data))
fun getAction(name: String, url: String, data: String) = Action("id", name, getIntegration(url, data))
fun getAttachment(text: String, name: String, url: String, data: String) =
    Attachment(text, listOf(getAction(name, url, data)))

class RespondMessageBuilder {
    val actions: MutableList<Action> = mutableListOf()
    var text = ""
    var data = ""
    fun addAction(name: String, url: String) {
        actions.add(getAction(name, url, data))
    }

    fun attachment() = Attachment(text, actions)
    fun build() = RespondMessage(attachments = listOf(attachment()))
}

fun respondMessage(builder: RespondMessageBuilder.() -> Unit) = RespondMessageBuilder().apply { builder() }.build()