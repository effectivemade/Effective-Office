package band.effective.office.tv.screen.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.network.UnsafeOkHttpClient
import band.effective.office.tv.repository.synology.SynologyRepository
import band.effective.office.tv.screen.autoplayController.AutoplayController
import band.effective.office.tv.screen.autoplayController.model.AutoplayState
import band.effective.office.tv.screen.autoplayController.model.OnSwitchCallbacks
import band.effective.office.tv.screen.autoplayController.model.ScreenState
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.screen.photo.model.toUIModel
import band.effective.office.tv.utils.StringResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: SynologyRepository,
    private val slideShow: TimerSlideShow,
    @UnsafeOkHttpClient val unsafeOkHttpClient: OkHttpClient,
    private val autoplayController: AutoplayController
) : ViewModel() {

    private val mutableState = MutableStateFlow(BestPhotoState.Empty)
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<BestPhotoEffect>()
    val effect = mutableEffect.asSharedFlow()

    init {
        slideShow.init(
            scope = viewModelScope,
            callbackToEnd = { mutableEffect.emit(BestPhotoEffect.ScrollToNextItem) },
            isPlay = true
        )
        viewModelScope.launch {
            repository.auth()
            updatePhoto()
        }

        autoplayController.addCallbacks(screen = Screen.BestPhoto, callbacks = object : OnSwitchCallbacks{
            override fun onForwardSwitch(controllerState: AutoplayState) {
                if (autoplayController.state.value.screenState.isPlay) slideShow.startTimer()
                //TODO(Maksim Mishenko) @Artem Gruzdev add switch to first and last photo
                mutableState.update { it.copy(isPlay = autoplayController.state.value.screenState.isPlay) }
            }

            override fun onBackSwitch(controllerState: AutoplayState) {
                if (autoplayController.state.value.screenState.isPlay) slideShow.startTimer()
                //TODO(Maksim Mishenko) @Artem Gruzdev add switch to first and last photo
                mutableState.update { it.copy(isPlay = autoplayController.state.value.screenState.isPlay) }
            }

            override fun onLeave() {
                slideShow.stopTimer()
            }
        })
    }

    fun sendEvent(event: BestPhotoEvent) {
        when (event) {
            is BestPhotoEvent.OnClickNextItem -> {
                slideShow.resetTimer()
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index + 1))
                }
            }
            is BestPhotoEvent.OnClickPreviousItem -> {
                slideShow.resetTimer()
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ScrollToItem(event.index - 1))
                }
            }
            BestPhotoEvent.OnClickPlayButton -> {
                val isPlay = !mutableState.value.isPlay
                mutableState.update {
                    it.copy(isPlay = isPlay)
                }
                if (!isPlay) slideShow.stopTimer()
                else {
                    slideShow.startTimer()
                }
                viewModelScope.launch {
                    mutableEffect.emit(BestPhotoEffect.ChangePlayState(isPlay))
                }
            }
            is BestPhotoEvent.OnRequestSwitchScreen -> {
                slideShow.stopTimer()
                when (event.request) {
                    NavigateRequests.Forward -> autoplayController.nextScreen(
                        state.value.toScreenModel(
                            true
                        )
                    )
                    NavigateRequests.Back -> autoplayController.prevScreen(
                        state.value.toScreenModel(
                            false
                        )
                    )
                }
            }
        }
    }

    private suspend fun updatePhoto() {
        mutableState.update { state ->
            state.copy(isLoading = true)
        }

        repository.getPhotosUrl().collect { result ->
            when (result) {
                is Either.Failure -> {
                    autoplayController.addError(
                        screen = Screen.BestPhoto,
                        errorText = StringResource.DynamicResource(result.error)
                    )
                    mutableState.update { state ->
                        state.copy(isError = true, error = result.error, isLoading = false)
                    }
                }
                is Either.Success -> {
                    mutableState.update { state ->
                        state.copy(
                            photos = result.toUIModel(),
                            isError = false,
                            isLoading = false,
                            isData = true
                        )
                    }
                    slideShow.startTimer()
                }
            }
        }
    }

    private fun BestPhotoState.toScreenModel(direction: Boolean): ScreenState =
        ScreenState(isPlay = isPlay, isForwardDirection = direction)
}
