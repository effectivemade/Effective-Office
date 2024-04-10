package band.effective

import band.effective.mattermost.MattermostRepositoryImpl
import band.effective.synology.SynologyRepositoryImpl
import band.effective.utils.getEnv
import kotlinx.coroutines.*
import java.util.function.Function


class Handler : Function<Unit, Unit> {
    override fun apply(p0: Unit) =
        runBlocking {
            botLogic(coroutineScope = this)
        }
}

fun main (): Unit = runBlocking {
    botLogic(this)
}

suspend fun botLogic(coroutineScope: CoroutineScope) {
    val mattermostRepository = MattermostRepositoryImpl(
            token = getEnv(MattermostSettings.mattermostToken),
            coroutineScope = coroutineScope
    )
    val synologyRepository = SynologyRepositoryImpl()
    val botManager = BotManager(
        mattermost = mattermostRepository,
        coroutineScope = coroutineScope,
        synology = synologyRepository
    )
    botManager.updatePhoto()
}

