package band.effective.office.tvServer.repository.mattermost

import band.effective.office.tvServer.model.MattermostMessage

interface MattermostRepository {
    suspend fun writeMessage(chanelId: String, message: String)
    suspend fun readMessage(messageId: String): MattermostMessage
    suspend fun answerOnMessage(chanelId: String,rootId: String, message: String)
}