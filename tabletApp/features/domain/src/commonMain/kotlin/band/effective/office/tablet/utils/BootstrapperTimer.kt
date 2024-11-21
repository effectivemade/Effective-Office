package band.effective.office.tablet.utils

import band.effective.office.tablet.domain.useCase.TimerUseCase
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapperScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMviKotlinApi::class)
class BootstrapperTimer<T : Any>(private val timerUseCase: TimerUseCase) {

    private var job: Job? = null
    private var timerBootstrapper: CoroutineBootstrapperScope<T>? = null
    private var tickHandler: suspend CoroutineBootstrapperScope<T>.(Long) -> Unit = {}
    private var timerDelay: Duration? = null

    fun init(
        bootstrapperScope: CoroutineBootstrapperScope<T>,
        delay: Duration,
        handler: suspend CoroutineBootstrapperScope<T>.(Long) -> Unit,
    ) {
        timerBootstrapper = bootstrapperScope
        tickHandler = handler
        timerDelay = delay
    }

    fun start(
        bootstrapperScope: CoroutineBootstrapperScope<T>,
        delay: Duration,
        handler: suspend CoroutineBootstrapperScope<T>.(Long) -> Unit,
    ) {
        init(bootstrapperScope, delay, handler)
        restart()
    }

    fun restart(delay: Duration = timerDelay ?: 1.seconds) {
        if (timerBootstrapper != null) {
            job?.cancel()
            job = timerBootstrapper!!.launch(Dispatchers.IO) {
                timerUseCase.timerFlow(delay).collect {
                    with(timerBootstrapper!!) { tickHandler(it) }
                }
            }
        }
    }

    fun stop() {
        job?.cancel()
    }

}