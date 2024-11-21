package band.effective.office.tv.domain.botLogic.model

sealed class BotEvent {
    abstract val messageId: String
    abstract val userId: String
    abstract val rootId: String
    abstract val channelId: String

    data class PostMessage(
        val message: String,
        override val messageId: String,
        val userName: String,
        override val userId: String,
        override val rootId: String,
        override val channelId: String
    ) : BotEvent()

    data class Reaction(
        override val messageId: String,
        override val userId: String,
        override val rootId: String,
        val emojiName: String,
        override val channelId: String
    ) : BotEvent()
}