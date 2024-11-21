package band.effective.office.tv.screen.menu.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import band.effective.office.tv.screen.menu.MenuOption

@Composable
fun ScreenList(
    modifier: Modifier = Modifier,
    isFocusOnMenu: Boolean,
    options: List<MenuOption>,
    content: @Composable BoxScope.(MenuOption) -> Unit
) {
    var index by remember { mutableStateOf(0) }
    var currentIndex by remember { mutableStateOf(0) }
    Box(modifier = modifier) {
        content(options[index])
        AnimatedVisibility(
            visible = isFocusOnMenu,
            enter = slideInHorizontally() + fadeIn(),
            exit = slideOutHorizontally() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0f to Color.Black,
                                0.3f to Color.Black,
                                1f to Color.Transparent
                            )
                        )
                    )
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            options.forEachIndexed { iconIndex, option ->
                if (iconIndex != 0) Spacer(modifier = Modifier.height(5.dp))
                val elementColor = when {
                    isFocusOnMenu && currentIndex == iconIndex -> MaterialTheme.colors.primaryVariant
                    index == iconIndex -> MaterialTheme.colors.secondaryVariant
                    else -> Color.Transparent
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .animateContentSize()
                        .onFocusChanged { if (it.isFocused) currentIndex = iconIndex }
                        .clip(RoundedCornerShape(10.dp))
                        .background(elementColor)
                        .clickable { index = iconIndex },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = option.icon),
                        contentDescription = null,
                    )
                    if (isFocusOnMenu) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            modifier = Modifier.width(150.dp),
                            text = stringResource(id = option.title),
                            color = Color.White,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }
}