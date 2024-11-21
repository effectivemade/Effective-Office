package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import band.effective.office.tv.ui.theme.IndependentColors

@Composable
fun StoryIndicator(countStories: Int, currentStoryIndex: Int, modifier: Modifier, progress: Float = 1f) {

    val screenConfiguration = LocalConfiguration.current
    val screenWidth = screenConfiguration.screenWidthDp.dp
    val indicatorWidth = (screenWidth - 64.dp - (countStories - 1) * 8.dp) / (countStories)

    ProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        elementModifier = Modifier.clip(RoundedCornerShape(12.dp)).width(indicatorWidth),
        count = countStories, currentIndex = currentStoryIndex,
        elementColor = IndependentColors.StoryActiviteIndicatorGray,
        currentElementColor = IndependentColors.StoryIndicatorGray,
        progress = progress
    )

}

@Preview
@Composable
fun PreviewStoryIndicator() {
    StoryIndicator(
       5, 1, Modifier.padding(24.dp)
    )
}