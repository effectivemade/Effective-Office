package band.effective.feedback.presentation

import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.presentation.model.Reaction

class Reactor(private val mattermostApi: MattermostApi) {
    suspend fun makeReaction(postId: String, reaction: Reaction) {
        mattermostApi.makeReaction(
            userId = mattermostApi.me().id,
            postId = postId,
            reactionName = reaction.reactionName
        )
    }

    suspend inline fun <T> Result<T>.foldWithReaction(
        postId: String,
        onSuccess: (T) -> Unit,
        onFailure: (Throwable) -> Unit
    ) =
        fold(
            onSuccess = {
                onSuccess(it)
                makeReaction(postId, Reaction.SUCCESS)
            },
            onFailure = {
                onFailure(it)
                makeReaction(postId, Reaction.FAIL)
            }
        )
}