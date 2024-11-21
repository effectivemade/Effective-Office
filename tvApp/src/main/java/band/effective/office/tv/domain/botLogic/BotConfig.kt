package band.effective.office.tv.domain.botLogic

import band.effective.office.tv.BuildConfig

object BotConfig {
    const val importantMessageReaction = "warning"
    const val deleteMessageReaction = "no_entry_sign"
    const val importantMessageDelay = 60
    const val commonMessageDelay = 30
    const val maxMessageLength = 500
    const val directId = BuildConfig.mattermostBotDirectId
}