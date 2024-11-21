package band.effective.office.tv.core.ui.screen_with_controls.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuButton
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuState
import kotlinx.coroutines.flow.update


@Composable
fun SlideShowPhotoControl(
    currentListPosition: Int,
    countItems: Int,
    isPlay: Boolean,
    modifier: Modifier = Modifier,
    prevButton: FocusRequester,
    nextButton: FocusRequester,
    playButton: FocusRequester,
    backToPhoto: FocusRequester,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit,
) {
    val state by MenuState.state.collectAsState()
    LaunchedEffect(Unit) {
        when (state.selectButton) {
            MenuButton.Play -> playButton
            MenuButton.Nothink -> backToPhoto
            MenuButton.Prev -> prevButton
            MenuButton.Next -> nextButton
        }.requestFocus()
    }
    Row(
        modifier = modifier.focusable()
    ) {
        ButtonControls(isFocus = state.selectButton == MenuButton.Prev,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .focusRequester(prevButton)
                .onFocusChanged { state ->
                    if (state.isFocused) MenuState.state.update { it.copy(selectButton = MenuButton.Prev) }
                }
                .focusProperties {
                    up = backToPhoto
                    down = backToPhoto
                    previous = backToPhoto
                    right = playButton
                    left = nextButton
                    next = playButton
                }
                .focusable()
                .size(70.dp),
            idActiveIcon = R.drawable.ic_previous_active,
            idInactiveIcon = R.drawable.ic_previous_inactive,
            onClick = {
                onClickPreviousItemButton()
            })

        ButtonControls(isFocus = state.selectButton == MenuButton.Play,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .focusRequester(playButton)
                .onFocusChanged { state ->
                    if (state.isFocused) MenuState.state.update { it.copy(selectButton = MenuButton.Play) }
                }
                .focusProperties {
                    up = backToPhoto
                    down = backToPhoto
                    previous = backToPhoto
                    left = prevButton
                    right = nextButton
                    next = nextButton
                }
                .focusable()
                .size(80.dp),
            idActiveIcon = if (!isPlay) R.drawable.ic_play_active else R.drawable.ic_pause_active,
            idInactiveIcon = if (!isPlay) R.drawable.ic_play_inactive else R.drawable.ic_pause_inactive,
            onClick = onClickPlayButton

        )
        ButtonControls(isFocus = state.selectButton == MenuButton.Next,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .focusRequester(nextButton)
                .onFocusChanged { state ->
                    if (state.isFocused) MenuState.state.update { it.copy(selectButton = MenuButton.Next) }
                }
                .focusProperties {
                    up = backToPhoto
                    down = backToPhoto
                    previous = backToPhoto
                    right = prevButton
                    left = playButton
                }
                .focusable()
                .size(70.dp),
            idActiveIcon = R.drawable.ic_next_active,
            idInactiveIcon = R.drawable.ic_next_inactive,
            onClick = {
                onClickNextItemButton()
            })
    }
}
