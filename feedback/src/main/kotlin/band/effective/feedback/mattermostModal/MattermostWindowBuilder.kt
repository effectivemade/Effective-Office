package band.effective.feedback.mattermostModal


class MattermostWindowBuilder {
    var title: String = ""
    var elements: MutableList<Element> = mutableListOf()
    var introduction_text: String = ""
    var submit_label: String = ""
    var state = ""

    fun text(
        display_name: String = "",
        name: String = "",
        subtype: String = "",
        placeholder: String = "",
        help_text: String = "",
        multiline: Boolean = false
    ) {
        elements.add(
            if (multiline) Element.Textarea(display_name, name, subtype, placeholder, help_text)
            else Element.Text(display_name, name, subtype, placeholder, help_text)
        )
    }

    fun build() = MattermostWindow(
        title = title,
        elements = elements.map { it as Element.Text },
        introduction_text = introduction_text,
        submit_label = submit_label,
        state = state
    )
}

fun MattermostWindow(builder: MattermostWindowBuilder.() -> Unit) =
    MattermostWindowBuilder().apply { builder() }.build()