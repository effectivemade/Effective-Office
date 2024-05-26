package band.effective.feedback.utils

import band.effective.feedback.data.mattermost.MattermostApi

private const val TEAM = "effectiveband"

suspend fun MattermostApi.getDirect(id1: String, id2: String, teamName: String = TEAM) =
    runCatching { getChanel("${id1}__$id2", teamName) }
        .recoverCatching { getChanel("${id2}__$id1", teamName) }
        .getOrElse { createChannel(id1, id2) }