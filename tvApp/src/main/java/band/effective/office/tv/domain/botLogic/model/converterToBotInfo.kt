package band.effective.office.tv.domain.botLogic.model

import band.effective.office.tv.network.mattermost.model.AuthJson

fun AuthJson.toBotInfo(): BotInfo = BotInfo(id = id, name = username)