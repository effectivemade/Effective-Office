package band.effective.office.tvServer.repository.mattermost

import band.effective.office.tvServer.api.mattermost.MattermostApi
import band.effective.office.tvServer.api.mattermost.model.CreatePostRequest
import band.effective.office.tvServer.api.mattermost.model.GetPostRespond
import band.effective.office.tvServer.model.MattermostMessage
import band.effective.office.tvServer.model.MattermostUser

class ChatRepository(private val api: MattermostApi) : MattermostRepository {
    override suspend fun writeMessage(chanelId: String, message: String) {
        api.createPost(request = CreatePostRequest(channel_id = chanelId, message = message))
    }

    override suspend fun readMessage(messageId: String): MattermostMessage {
        return api.getPost(postId = messageId).toMessage()
    }

    override suspend fun answerOnMessage(chanelId: String, rootId: String, message: String) {
        api.createPost(request = CreatePostRequest(channel_id = chanelId, message = message, root_id = rootId))
    }

    private suspend fun GetPostRespond.toMessage(): MattermostMessage = api.getUser(user_id).let { user ->
        MattermostMessage(
            channelId = channel_id,
            id = id,
            text = message,
            rootId = root_id,
            author = MattermostUser(id = user.id, name = "${user.first_name} ${user.last_name}")
        )
    }
}
