package band.effective.office.tv.screen.autoplayMenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.screen.autoplayController.AutoplayController
import band.effective.office.tv.screen.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoplayMenuViewModel @Inject constructor(
    val autoplayController: AutoplayController,
    private val timer: TimerSlideShow
) : ViewModel() {
    private var mutableState =
        MutableStateFlow(AutoplayMenuState.defaultState.copy(screenList = autoplayController.state.value.screensList))
    val state = mutableState.asStateFlow()

    //TODO(Maksim Mishenko) inject controller
    var navController: NavController? = null

    init {
        viewModelScope.launch {
            timer.process.collect { progress -> mutableState.update { it.copy(autoClickProgress = progress) } }
        }
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                startAutoplay()
            },
            isPlay = true
        )
        timer.startTimer()
    }

    fun addScreen(screen: Screen) {
        mutableState.update { it.copy(screenList = it.screenList + screen) }
    }

    fun removeScreen(screen: Screen) {
        mutableState.update { it.copy(screenList = it.screenList - screen) }
    }

    fun startAutoplay() {
        viewModelScope.launch {
            if (navController == null || !state.value.isDraw) return@launch
            autoplayController.resetController()
            if (state.value.screenList.isNotEmpty()) {
                state.value.screenList.forEach { autoplayController.registerScreen(it) }
                mutableState.update { it.copy(isDraw = false) }
                navController?.navigate(Screen.Autoplay.name)
            }
        }

    }

    fun stopTimer() {
        timer.stopTimer()
    }

    fun onDraw() {
        mutableState.update { it.copy(isDraw = true) }
    }

    fun timerIsActive() = timer.isPlay
}