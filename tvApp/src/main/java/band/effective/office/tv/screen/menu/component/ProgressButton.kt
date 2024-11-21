package band.effective.office.tv.screen.menu.component

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    process: Float,
    progressColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit = {},
    onFocus: () -> Unit = {},
    onFocusLeave: () -> Unit = {},
    content: @Composable (Boolean) -> Unit = {}
) {
    var isFocus by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocus = focusState.isFocused
                if (isFocus) onFocus()
                else onFocusLeave()
            }
            .focusable(),
        onClick = onClick
    ) {
        LinearProgressIndicator(
            progress = process,
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            backgroundColor = backgroundColor
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content(isFocus)
        }
    }
}

@Preview
@Composable
fun ProgressButtonPreview() {
    ProgressButton(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(300.dp)),
        process = 0.6f,
        progressColor = Color.Green,
        backgroundColor = Color.White
    ) {
        Text("Press")
    }
}