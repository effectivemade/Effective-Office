package band.effective.office.tv.screen.menu.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.focusable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun ButtonAutoplay(
    modifier: Modifier = Modifier,
    onFocus: () -> Unit = {},
    onClick: () -> Unit = {},
    content: @Composable (Boolean) -> Unit = {}
) {
    var isFocus by remember { mutableStateOf(false) }
    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isFocus) MaterialTheme.colors.primaryVariant
        else MaterialTheme.colors.secondaryVariant, label = ""
    )
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(animatedBackgroundColor),
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocus = focusState.isFocused
                if (isFocus) onFocus()
            }
            .focusable()) {
        content(isFocus)
    }
}