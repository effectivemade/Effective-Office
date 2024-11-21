package band.effective.office.tv.domain.botLogic.impl

import band.effective.office.tv.R
import band.effective.office.tv.domain.botLogic.BotConfig
import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.domain.model.message.toBotMessage
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.utils.RStringGetter
import band.effective.office.tv.utils.calendarToString
import band.effective.office.tv.utils.fullDay
import band.effective.office.tv.utils.getDate
import band.effective.office.tv.utils.tomorrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.GregorianCalendar
import javax.inject.Inject

class MattermostBot @Inject constructor(
    private val client: MattermostWebSocketClient,
    private val stringGetter: RStringGetter
) :
    MessengerBot {
    private lateinit var scope: CoroutineScope
    override fun start(scope: CoroutineScope) {
        this.scope = scope
        initBot()
    }

    private fun initBot() = scope.launch {
        if (client.connect()) {
            client.getDirectMessages().forEach { message ->
                if (message.finish.after(GregorianCalendar())) {
                    MessageQueue.secondQueue.push(message)
                } else {
                    client.deleteMessage(message.directId)
                }
            }
            client.subscribe { event ->
                handler(event)
            }
        }
    }

    private fun handler(event: BotEvent) = scope.launch {
        when (event) {
            is BotEvent.PostMessage -> {
                if (event.message.isForBot()) {
                    val message = event.toBotMessage()
                    when {
                        message.isPinCommand() -> {
                            if (pinMessage(message))
                                message.answer("${stringGetter.getString(R.string.bot_pin_message)} ${calendarToString(message.finish,"dd.MM.YY HH:mm")}")
                            else
                                message.answer(stringGetter.getString(R.string.bot_get_message_error))
                        }
                        !message.fromThread() -> {
                            if (addMessage(message.copy(finish = tomorrow())))
                                message.answer(stringGetter.getString(R.string.bot_get_message))
                            else
                                message.answer(stringGetter.getString(R.string.bot_get_message_error))
                        }
                    }
                }
            }
            is BotEvent.Reaction -> {
                when (event.emojiName) {
                    BotConfig.importantMessageReaction -> {
                        incrementImportant(event.messageId, event.userId)
                    }
                    BotConfig.deleteMessageReaction -> {
                        if (deleteMessage(event.messageId, event.userId))
                            client.getMessage(event.messageId).answer(stringGetter.getString(R.string.bot_delete_message))
                        else
                            client.getMessage(event.messageId)
                                .answer(stringGetter.getString(R.string.bot_delete_message_error))
                    }
                }
            }
        }
    }

    /**Send message in chat
     * @param message chanelId, text and thread for post, other message's fields no matter*/
    private fun postMessage(message: BotMessage) = scope.launch {
        client.postMessage(
            channelId = message.channelId, message = message.text, root = message.rootId
        )
    }

    /**Check that it's message for bot*/
    private fun String.isForBot(): Boolean = contains(client.botInfo().name)

    /**Send answer on message*/
    private fun BotMessage.answer(text: String) = postMessage(copy(text = text))

    /**Filter message's text from useless information*/
    private fun BotMessage.filter() = copy(
        text = text.replace(
            "@${client.botInfo().name}", ""
        ).replace(stringGetter.getString(R.string.bot_pin_command), "").trim(' ', ',', '.')
    )

    /**Check that message is command for pin message on later date*/
    private fun BotMessage.isPinCommand() =
        text.lowercase().contains(stringGetter.getString(R.string.bot_pin_command)) && filter().text.getDate("dd MMM")
            .after(GregorianCalendar()) && rootId != ""

    /**Copy message from common message queue to important message queue
     * @param messageId message id
     * @param userId user incrementing important*/
    private suspend fun incrementImportant(messageId: String, userId: String) {
        if (MessageQueue.firstQueue.contain(messageId)) {
            return
        }
        var message = MessageQueue.secondQueue.message(messageId)
        if (message == null) {
            message = BotMessage.deletedMessage.firstOrNull() { it.id == messageId }
        } else {
            if (message.author.id != userId) return
            MessageQueue.secondQueue.removeMessage(message)
            client.deleteMessage(message.directId)
        }
        if (message != null && message.author.id == userId) {
            MessageQueue.firstQueue.push(message.copy(directId = ""))
            message.answer(stringGetter.getString(R.string.bot_increment_important))
            deleteMessage(messageId,userId)
        }
    }

    /**Pin message on custom time and if need add message in common queue
     * @param message pinning message
     * @return true if complete pin else false*/
    private suspend fun pinMessage(message: BotMessage): Boolean {
        val pinningMessage =
            MessageQueue.secondQueue.message(message.rootId)
                ?: client.getMessage(message.rootId)
        pinningMessage.finish =
            message.filter().text.getDate("dd MMM").fullDay()
        if (!MessageQueue.secondQueue.contain(pinningMessage.id)) {
            return addMessage(pinningMessage)
        }
        return true
    }

    /**Add message in common queue
     * @param message adding message
     * @return true if complete add else false*/
    private suspend fun addMessage(message: BotMessage): Boolean {
        val correctMessage = message.filter().copy(directId = BotConfig.directId)
        if (correctMessage.text.length > BotConfig.maxMessageLength) {
            return false
        }
        MessageQueue.secondQueue.push(client.saveMessage(correctMessage))
        return true
    }

    /**Delete message from all queues and saved messages
     * @param messageId deleting message id
     * @param userId user deleting message, only author can delete message
     * @return true if complete delete else false*/
    private suspend fun deleteMessage(messageId: String, userId: String): Boolean {
        val message = MessageQueue.secondQueue.message(messageId)
            ?: MessageQueue.firstQueue.message(messageId)
            ?: BotMessage.deletedMessage.firstOrNull { it.id == messageId }
        if (message != null && message.directId != "")
            client.deleteMessage(message.directId)
        if ((message != null) && (message.author.id == userId)) {
            MessageQueue.secondQueue.removeMessage(message)
            MessageQueue.firstQueue.removeMessage(message)
            BotMessage.deletedMessage.remove(message)
            return true
        }
        return false
    }

    /**Check that message from thread*/
    private fun BotMessage.fromThread(): Boolean = rootId != id
}
