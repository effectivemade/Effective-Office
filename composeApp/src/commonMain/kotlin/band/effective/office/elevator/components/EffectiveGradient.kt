package band.effective.office.elevator.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.EffectiveTheme

@Composable
fun EffectiveGradient(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blur(radius = 200.dp)
            .background(EffectiveTheme.colors.divider.primary)
    ) {
        val colors = EffectiveTheme.colors

        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 4
            val topMargin = size.height / 5
            drawCircle(
                color = colors.graph.violet,
                radius = radius,
                center = Offset(x = size.width / 2, y = topMargin)
            )
            drawCircle(
                color = colors.graph.orange,
                radius = radius,
                center = Offset(x = size.width / 2, y = topMargin + radius * 2)
            )
        }
    }
}
