package band.effective.office.tv.screen.menu.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import band.effective.office.tv.ui.theme.robotoFontFamily

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFocus: (Boolean) -> Unit,
    content: @Composable BoxScope.() -> Unit = {}
) {
    var isFocus by remember { mutableStateOf(false) }

    val animatedBackgroundColor by animateColorAsState(
        targetValue = if (isFocus) MaterialTheme.colors.primaryVariant
        else MaterialTheme.colors.secondaryVariant
    )

    Box(modifier = modifier
        .background(animatedBackgroundColor)
        .fillMaxHeight(0.9f)
        .onFocusChanged {
            isFocus = it.isFocused
            onFocus(it.isFocused)
        }
        .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun TextMenuItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onFocus: (Boolean) -> Unit
) {
    MenuItem(
        modifier = modifier,
        onClick = { onClick() },
        onFocus = { onFocus(it) },
    ) {
        Text(
            text = text, color = Color.White, fontSize = 30.sp, fontFamily = robotoFontFamily()
        )
    }
}