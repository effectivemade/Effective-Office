package band.effective.office.tv.screen.message.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.screen.components.ProgressIndicator
import coil.ImageLoader

@Composable
fun ManyMessagesScreen(
    modifier: Modifier,
    imageLoader: ImageLoader,
    messagesList: List<BotMessage>,
    currentIndex: Int = 0,
    textColor: Color = Color.Black,
    onClickButton: (() -> Unit)? = null,
    messageProcess: Float
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            ProgressIndicator(
                modifier = Modifier,
                elementModifier = Modifier.width(1.dp).clip(CircleShape),
                count = messagesList.size,
                currentIndex = currentIndex,
                progress = messageProcess
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            OneMessageScreen(
                modifier = modifier,
                imageLoader = imageLoader,
                message = messagesList[currentIndex],
                onClickBackButton = onClickButton,
                textColor = textColor
            )
        }
    }
}
