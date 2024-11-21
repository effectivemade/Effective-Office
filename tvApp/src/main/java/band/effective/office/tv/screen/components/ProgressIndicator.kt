package band.effective.office.tv.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarCell(
    modifier: Modifier = Modifier,
    index: Int,
    current: Int,
    progress: Float,
    elementColor: Color,
    currentElementColor: Color
) {
    LinearProgressIndicator(
        progress = when {
            index < current -> 1f
            index == current -> progress
            else -> 0f
        },
        modifier = modifier,
        color = currentElementColor,
        backgroundColor = elementColor
    )
}

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    elementModifier: Modifier = Modifier,
    count: Int,
    currentIndex: Int,
    progress: Float = 1f,
    elementColor: Color = MaterialTheme.colors.secondary,
    currentElementColor: Color = Color.White
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 0 until count) {
            ProgressBarCell(
                modifier = elementModifier.weight(1f),
                index = index,
                current = currentIndex,
                progress = progress,
                elementColor = elementColor,
                currentElementColor = currentElementColor
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun ProgressIndicatorPreview() {
    ProgressIndicator(elementModifier = Modifier.clip(RoundedCornerShape(12.dp)),count = 10, currentIndex = 1, progress = 0.4f)
}