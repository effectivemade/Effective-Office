package band.effective.feedback.mattermostModal

import kotlinx.serialization.Serializable

@Serializable
sealed interface Element {
    @Serializable
    data class Text(
        val display_name: String,
        val name: String,
        val subtype: String,
        val placeholder: String,
        val help_text: String,
        val type: String = "text"
    ) : Element

    @Serializable
    data class Textarea(
        val display_name: String,
        val name: String,
        val subtype: String,
        val placeholder: String,
        val help_text: String,
        val type: String = "textarea"
    ) : Element
}