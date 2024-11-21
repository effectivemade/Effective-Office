package band.effective.office.tablet.ui.loader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.core.MainRes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Loader(elementColor: Color = MaterialTheme.colors.onPrimary) {
    var list by remember { mutableStateOf(List(5) { false }) }
    LaunchedEffect(Unit) {
        launch {
            var delayDuration: Long
            while (true) {
                list.forEachIndexed { index, b ->
                    list =
                        list.toMutableList().apply { this[4 - index] = !b }
                    delayDuration = when {
                        index == 0 && b -> 600L
                        b -> 100L
                        else -> 200L
                    }
                    delay(delayDuration)
                }
                delay(1000L)
            }
        }
    }
    val localDensity = LocalDensity.current
    Row() {
        list.forEach {
            AnimatedVisibility(
                visible = it,
                enter = slideInHorizontally {
                    localDensity.run { localDensity.run { -300.dp.toPx() } }.toInt()
                } + fadeIn(),
                exit = slideOutHorizontally {
                    localDensity.run { localDensity.run { 300.dp.toPx() } }.toInt()
                } + fadeOut()
            ) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.loader_element),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(elementColor)
                )
            }
        }
    }
}