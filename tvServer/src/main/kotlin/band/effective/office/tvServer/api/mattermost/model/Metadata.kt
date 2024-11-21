package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    val acknowledgements: List<Acknowledgement>,
    val embeds: List<Embed>,
    val emojis: List<Emoji>,
    val files: List<File>,
    val priority: Priority,
    val reactions: List<Reaction>
)