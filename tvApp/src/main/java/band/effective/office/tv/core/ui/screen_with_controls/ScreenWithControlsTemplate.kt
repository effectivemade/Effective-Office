package band.effective.office.tv.core.ui.screen_with_controls

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import band.effective.office.tv.core.ui.screen_with_controls.components.SlideShowPhotoControl
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuState
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScreenWithControlsTemplate(
    currentListPosition: Int,
    countItems: Int,
    isPlay: Boolean,
    playButton: FocusRequester,
    contentFocus: FocusRequester,
    modifier: Modifier = Modifier,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val state by MenuState.state.collectAsState()
    val (prevButton, nextButton) = remember { FocusRequester.createRefs() }
    Box(
        modifier = modifier
            .onKeyEvent {
                Log.d("BUTTON", "click button ${it.key.keyCode}")
                if ((listOf(Key.DirectionCenter, Key.Enter).contains(it.key)
                            || (it.key == Key.NavigatePrevious && state.isVisible))
                    && it.type == KeyEventType.KeyDown
                ) {
                    MenuState.state.update { it.copy(isVisible = !it.isVisible) }
                }
                return@onKeyEvent false
            }
            .fillMaxSize(),
    ) {
        content()
        AnimatedVisibility(
            visible = state.isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        ) {
            SlideShowPhotoControl(
                currentListPosition = currentListPosition,
                countItems = countItems,
                isPlay = isPlay,
                prevButton = prevButton,
                nextButton = nextButton,
                playButton = playButton,
                backToPhoto = contentFocus,
                onClickPlayButton = {
                    onClickPlayButton()
                },
                onClickNextItemButton = {
                    onClickNextItemButton()
                },
                onClickPreviousItemButton = {
                    onClickPreviousItemButton()
                }
            )
        }
    }
}