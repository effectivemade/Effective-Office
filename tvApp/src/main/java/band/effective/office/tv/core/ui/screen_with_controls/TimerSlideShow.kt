package band.effective.office.tv.core.ui.screen_with_controls

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class TimerSlideShow @Inject constructor() {
    private var timerJob: Job? = null
    private var period = SliderProperties.slideShowPeriod
    private var scope: CoroutineScope? = null
    private lateinit var callbackToEnd: suspend () -> Unit
    var isPlay: Boolean = false

    fun init(
        scope: CoroutineScope,
        callbackToEnd: suspend () -> Unit,
        isPlay: Boolean = false,
        period: Int = SliderProperties.slideShowPeriod
    ) {
        this.scope = scope
        this.callbackToEnd = callbackToEnd
        this.isPlay = isPlay
        this.period = period
    }

    fun startTimer() {
        isPlay = true
        timerJob?.cancel()
        timerJob = timerJob()
    }

    private var mutableProgress = MutableStateFlow(0f)
    val process = mutableProgress.asStateFlow()

    //TODO(Stanislav Radchenko): @Artem Gruzdev improve this code
    private fun timerJob() = scope?.launch(Dispatchers.Default) {
        while (isActive && isPlay) {
            val startTime = System.currentTimeMillis()
            var currentTime = System.currentTimeMillis()
            while (isActive && currentTime - startTime < period * 1000) {
                mutableProgress.update { (currentTime - startTime).toFloat() / (period * 1000) }
                currentTime = System.currentTimeMillis()
            }
            if (isActive && isPlay)
                callbackToEnd()
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerJob = timerJob()
    }

    fun stopTimer() {
        isPlay = false
        timerJob?.cancel()
    }
}