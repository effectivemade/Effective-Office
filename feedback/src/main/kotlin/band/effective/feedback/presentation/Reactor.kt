package band.effective.feedback.presentation

import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.presentation.model.Reaction
import band.effective.feedback.presentation.model.WebHookDto
import band.effective.feedback.utils.getDirect

class Reactor(private val mattermostApi: MattermostApi) {
    suspend fun makeReaction(dto: WebHookDto, reaction: Reaction) {
        try {
            mattermostApi.makeReaction(
                userId = mattermostApi.me().id,
                postId = dto.post_id,
                reactionName = reaction.reactionName
            )
        } catch (e: Throwable) {
            val bot = mattermostApi.me()
            val direct = mattermostApi.getDirect(
                id1 = bot.id,
                id2 = mattermostApi.getUserInfo(dto.user_name).id
            )
            val message = mattermostApi.writeMessage(channelId = direct.id, message = dto.text.replace("/", ""))
            mattermostApi.makeReaction(userId = bot.id, postId = message.id, reactionName = reaction.reactionName)
        }
    }

    suspend inline fun <T,R> Result<T>.foldWithReaction(
        dto: WebHookDto,
        onSuccess: (T) -> R,
        onFailure: (Throwable) -> R
    ) =
        fold(
            onSuccess = {
                val result = onSuccess(it)
                runCatching { makeReaction(dto, Reaction.SUCCESS) }.getOrNull()
                result
            },
            onFailure = {
                val result = onFailure(it)
                runCatching { makeReaction(dto, Reaction.FAIL) }.getOrNull()
                result
            }
        )
}