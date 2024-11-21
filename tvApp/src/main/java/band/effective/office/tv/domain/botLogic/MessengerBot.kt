package band.effective.office.tv.domain.botLogic

import kotlinx.coroutines.CoroutineScope

interface MessengerBot {
    /**Start mattermost bot
     * @param scope CoroutineScope for bot*/
    fun start(scope: CoroutineScope)
}