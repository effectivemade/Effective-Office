package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class MetadataXX(
    val acknowledgements: List<AcknowledgementX>,
    val embeds: List<EmbedX>,
    val emojis: List<EmojiX>,
    val files: List<FileX>,
    val priority: PriorityXX,
    val reactions: List<ReactionX>
)