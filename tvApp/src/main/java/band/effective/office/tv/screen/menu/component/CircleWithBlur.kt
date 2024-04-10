package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleWithBlur(modifier: Modifier = Modifier, xOffset: Dp, color: Color) {
    val conf = LocalConfiguration.current
    Canvas(
        modifier = modifier
            .offset(xOffset, -conf.screenHeightDp.dp / 2 - 100.dp)
            .fillMaxSize(),
        onDraw = {
            scale(2f) {
                drawCircle(
                    Brush.radialGradient(
                        colors = listOf(color, Color.Transparent),
                        radius = conf.screenWidthDp.dp.value / 2
                    )
                )
            }
        }
    )
}