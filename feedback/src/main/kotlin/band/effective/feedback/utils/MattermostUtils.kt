package band.effective.feedback.utils

import band.effective.feedback.data.mattermost.MattermostApi
import band.effective.feedback.data.mattermost.NotFoundException

private const val TEAM = "effectiveband"

suspend fun MattermostApi.getDirect(id1: String, id2: String, teamName: String = TEAM) =
    runCatching {
        try {
            getChanel("${id1}__$id2", teamName)
        } catch (e: NotFoundException) {
            getChanel("${id2}__$id1", teamName)
        }
    }.fold(
        onSuccess = { it },
        onFailure = { createChannel(id1, id2) }
    )