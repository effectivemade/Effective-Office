package band.effective.office.tv.network.mattermost.mattermostWebSocketClient

import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.botLogic.model.BotInfo
import band.effective.office.tv.domain.model.message.BotMessage

interface MattermostWebSocketClient {
    /**Connect with mattermost*/
    suspend fun connect(): Boolean

    /**Disconnect from server*/
    fun disconnect()

    /**Subscribe on server events
     * @param handler events handler*/
    fun subscribe(handler: (BotEvent) -> Unit)

    /**
     * Send message in chat
     * @param channelId id of the chat you are sending the message
     * @param message text you are sending
     * @param root thread's id*/
    suspend fun postMessage(channelId: String, message: String, root: String = "")

    /**Save message in direct
     * @return message with correct directId*/
    suspend fun saveMessage(message: BotMessage):BotMessage

    /**Get bot name and id*/
    fun botInfo(): BotInfo

    /**Get messages saved in direct*/
    suspend fun getDirectMessages(): List<BotMessage>

    /**Get message by id*/
    suspend fun getMessage(messageId: String): BotMessage

    /**Delete message by id
     * @return true if message is delete*/
    suspend fun deleteMessage(messageId: String): Boolean
    /**Get user name by id*/
    suspend fun getUserName(userId: String): String
}