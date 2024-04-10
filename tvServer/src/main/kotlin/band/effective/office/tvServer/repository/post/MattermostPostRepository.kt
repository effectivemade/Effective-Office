package band.effective.office.tvServer.repository.post

import band.effective.office.tvServer.api.mattermost.MattermostApi
import band.effective.office.tvServer.api.mattermost.model.Author
import band.effective.office.tvServer.api.mattermost.model.CreatePostRequest
import band.effective.office.tvServer.api.mattermost.model.SaveMessageDto
import band.effective.office.tvServer.api.mattermost.model.UpdateRequest
import band.effective.office.tvServer.model.MattermostMessage
import band.effective.office.tvServer.model.MattermostUser
import band.effective.office.tvServer.model.SavedMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class MattermostPostRepository(
    private val mattermostApi: MattermostApi,
    private val directId: String
) : PostRepository {
    override suspend fun getPosts(): Flow<SavedMessage> = flow {
        mattermostApi.getPostsFromChannel(directId).order.forEach { postId ->
            mattermostApi.getPost(postId).saveMessage?.let { message ->
                message.toMessage()?.run { emit(this) }
            }
        }
    }

    override suspend fun addPost(post: SavedMessage) {
        mattermostApi.createPost(CreatePostRequest(channel_id = directId, message = "save", props = post.toDto()))
    }


    override suspend fun updatePost(post: SavedMessage) {
        val originalPost = mattermostApi.getPostsFromChannel(directId).run {
            val ids = order
            ids.map { mattermostApi.getPost(it) }.first { it.saveMessage?.id == post.message.id }
        }
        mattermostApi.updatePost(
            postId = originalPost.id,
            request = UpdateRequest(originalPost.id, message = "save", props = post.toDto())
        )
    }

    override suspend fun deletePost(id: String) {
        val postId = mattermostApi.getPostsFromChannel(directId).order.first() { postId ->
            mattermostApi.getPost(postId).saveMessage?.id == id
        }
        mattermostApi.deletePost(postId)
    }

    private fun SavedMessage.toDto() = SaveMessageDto(
        author = Author(id = message.author.id, name = message.author.name),
        channelId = message.channelId,
        directId = directId,
        finish = finish.toStr(),
        from_bot = null,
        id = message.id,
        rootId = message.rootId,
        start = start.toStr(),
        text = message.text
    )

    private fun SaveMessageDto.toMessage(): SavedMessage? =
        try {
            SavedMessage(
                message = MattermostMessage(
                    channelId = channelId!!,
                    author = MattermostUser(id = author!!.id, name = author.name),
                    id = id!!,
                    text = text!!,
                    rootId = rootId!!
                ),
                start = start!!.toDateTime(),
                finish = finish!!.toDateTime()
            )

        } catch (e: Exception) {
            null
        }

    private fun String.toDateTime(): LocalDateTime {
        val parts = split("-").map { it.toInt() }
        return LocalDateTime.of(
            parts[0],
            parts[1] + 1,
            parts[2],
            parts[3],
            parts[4]
        ) //TODO(Makisim Mishenko) refactor after migrate
    }

    private fun LocalDateTime.toStr() = buildString {
        append(year)
        append("-")
        append(monthValue - 1)
        append("-")
        append(dayOfMonth)
        append("-")
        append(hour)
        append("-")
        append(minute)
    }
}
