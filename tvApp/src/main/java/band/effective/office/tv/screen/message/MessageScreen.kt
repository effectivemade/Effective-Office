package band.effective.office.tv.screen.message

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import band.effective.office.tv.core.ui.screen_with_controls.ScreenWithControlsTemplate
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuButton
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuState
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.screen.message.component.EmptyMessageScreen
import band.effective.office.tv.screen.message.component.ManyMessagesScreen
import band.effective.office.tv.screen.message.component.OneMessageScreen
import coil.ImageLoader
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    messagesList: List<BotMessage> = listOf(),
    currentIndex: Int = 0,
    uselessFact: String = "",
    isPlay: Boolean,
    textColor: Color = Color.Black,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit,
    onClickButton: (() -> Unit)? = null,
    messageProgress: Float
) {
    val (contentFocus, playButton) = remember { FocusRequester.createRefs() }
    ScreenWithControlsTemplate(modifier = Modifier
        .fillMaxSize()
        .onFocusChanged { focusState ->
            if (focusState.isFocused) MenuState.state.update { it.copy(selectButton = MenuButton.Nothink) }
        },
        currentListPosition = currentIndex,
        countItems = messagesList.size,
        isPlay = isPlay,
        playButton = playButton,
        contentFocus = contentFocus,
        onClickPlayButton = { onClickPlayButton() },
        onClickNextItemButton = { onClickNextItemButton() },
        onClickPreviousItemButton = { onClickPreviousItemButton() }) {
        val modifierWithFocus = modifier
            .focusRequester(contentFocus)
            .focusProperties {
                down = playButton
            }
            .focusable()
        when (messagesList.size) {
            0 -> EmptyMessageScreen(modifier = modifierWithFocus, uselessFact = uselessFact)
            1 -> OneMessageScreen(
                modifier = modifierWithFocus,
                imageLoader = imageLoader,
                message = messagesList[0],
                onClickBackButton = onClickButton,
                textColor = textColor
            )
            else -> ManyMessagesScreen(
                modifier = modifierWithFocus,
                imageLoader = imageLoader,
                messagesList = messagesList,
                currentIndex = currentIndex,
                onClickButton = onClickButton,
                textColor = textColor,
                messageProcess = messageProgress
            )
        }
    }

}