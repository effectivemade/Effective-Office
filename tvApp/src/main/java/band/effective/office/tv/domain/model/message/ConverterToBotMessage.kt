package band.effective.office.tv.domain.model.message

import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.network.mattermost.model.Post
import band.effective.office.tv.network.mattermost.model.PostJson
import band.effective.office.tv.network.mattermost.model.ReactionJson
import java.util.GregorianCalendar

fun PostJson.toBotEvent(): BotEvent.PostMessage =
    BotEvent.PostMessage(
        message = data.post.message,
        messageId = data.post.id,
        userName = data.senderName,
        userId = data.post.userId,
        rootId = data.post.rootId,
        channelId = data.post.channelId
    )

fun ReactionJson.toBotEvent(): BotEvent.Reaction =
    BotEvent.Reaction(
        messageId = data.reaction.postId,
        userId = data.reaction.userId,
        rootId = data.reaction.postId,
        channelId = data.reaction.channelId,
        emojiName = data.reaction.emojiName
    )

fun BotEvent.PostMessage.toBotMessage(): BotMessage =
    BotMessage(
        channelId = channelId,
        author =  User(id = userId, name = userName),
        id = messageId,
        text = message,
        start =  GregorianCalendar(),
        finish =  GregorianCalendar(),
        rootId = if (rootId == "") messageId else rootId
    )

fun Post.toBotMessage(userName: String): BotMessage = BotMessage(
    channelId = channelId,
    author =  User(id = userId, name = userName),
    id = id,
    text = message,
    start =  GregorianCalendar(),
    finish =  GregorianCalendar(),
    rootId = if (rootId == "") id else rootId
)