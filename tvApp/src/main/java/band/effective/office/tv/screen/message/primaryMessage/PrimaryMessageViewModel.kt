package band.effective.office.tv.screen.message.primaryMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.botLogic.BotConfig
import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.network.MattermostClient
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimaryMessageViewModel @Inject constructor(
    private val bot: MessengerBot,
    private val timer: TimerSlideShow,
    @MattermostClient val imageLoader: ImageLoader
) :
    ViewModel() {
    private val mutableState = MutableStateFlow(PrimaryMessageScreenState.empty)
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch() {
            bot.start(viewModelScope)
            timer.process.collect { progress -> mutableState.update { it.copy(messageProcess = progress) } }
        }
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (state.value.currentMessage + 1 < state.value.messagesList.size) {
                    mutableState.update { it.copy(currentMessage = it.currentMessage + 1) }
                } else {
                    mutableState.update { PrimaryMessageScreenState.empty }
                }
            },
            isPlay = state.value.isPlay,
            period = BotConfig.importantMessageDelay
        )
        collectEvent()
    }

    private fun collectEvent() = viewModelScope.launch {
        val firstQueue = MessageQueue.firstQueue
        firstQueue.queue.collect {
            if (firstQueue.isNotEmpty()) {
                mutableState.update {
                    it.copy(
                        isEmpty = false,
                        messagesList = it.messagesList + firstQueue.top()
                    )
                }
                firstQueue.pop()
                timer.startTimer()
            }
        }
    }

    fun onEvent(event: PrimaryMessageScreenEvents) {
        timer.stopTimer()
        when (event) {
            is PrimaryMessageScreenEvents.OnClickNextButton -> {
                if (state.value.currentMessage + 1 < state.value.messagesList.size) {
                    mutableState.update { it.copy(currentMessage = it.currentMessage + 1) }
                } else {
                    mutableState.update { it.copy(currentMessage = 0) }
                }
                timer.startTimer()
            }

            is PrimaryMessageScreenEvents.OnClickPrevButton -> {
                if (state.value.currentMessage == 0) {
                    mutableState.update { it.copy(currentMessage = it.messagesList.size - 1) }
                } else {
                    mutableState.update { it.copy(currentMessage = it.currentMessage - 1) }
                }
                timer.startTimer()
            }

            is PrimaryMessageScreenEvents.OnClickPlayButton -> {
                mutableState.update { it.copy(isPlay = !it.isPlay) }
                if (state.value.isPlay) {
                    timer.startTimer()
                }
            }
        }
    }

    fun endShow() {
        mutableState.update { PrimaryMessageScreenState.empty }
    }
}