package band.effective.office.tv.screen.autoplayMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import band.effective.office.tv.R
import band.effective.office.tv.screen.menu.component.MenuComponent
import band.effective.office.tv.screen.menu.component.MenuItemType
import band.effective.office.tv.screen.menu.component.ProgressButton
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.Orange2
import band.effective.office.tv.ui.theme.robotoFontFamily

data class ItemRes(
    val text: String,
    val icon: Painter? = null,
    val activeIcon: Painter? = null,
) {}

@Composable
fun AutoplayMenuScreen(
    viewModel: AutoplayMenuViewModel = hiltViewModel(),
    navController: NavController,
    startAutoplat: Boolean = false
) {
    viewModel.navController = navController
    val state by viewModel.state.collectAsState()
    val buttonFocusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.autoplay_menu_title),
            fontFamily = robotoFontFamily(),
            fontSize = 50.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(50.dp))
        MenuComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            itemModifier = Modifier.clip(shape = RoundedCornerShape(20.dp)),
            itemsList = listOf(
                Pair(
                    Screen.BestPhoto,
                    ItemRes(
                        text = stringResource(R.string.best_photo_screen_title),
                        icon = painterResource(R.drawable.icon_photos_orange),
                        activeIcon = painterResource(R.drawable.icon_photos_white)
                    )
                ),
                Pair(
                    Screen.Events,
                    ItemRes(
                        text = stringResource(R.string.event_screen_title),
                        icon = painterResource(R.drawable.icon_event_orange),
                        activeIcon = painterResource(R.drawable.icon_event_white)
                    )
                ),
            ),
            onNavigate = {},
            menuItemType = MenuItemType.SelectableItem({ screen -> state.screenList.contains(screen) }) {
                if (it.second) {
                    viewModel.addScreen(it.first)
                } else {
                    viewModel.removeScreen(it.first)
                }
            })
        Spacer(modifier = Modifier.height(50.dp))

        val primaryVariant = MaterialTheme.colors.primaryVariant
        val secondaryVariant = MaterialTheme.colors.secondaryVariant
        var buttonColor by remember { mutableStateOf(primaryVariant) }

        ProgressButton(
            modifier = Modifier
                .focusRequester(buttonFocusRequester)
                .height(70.dp)
                .width(300.dp)
                .clip(shape = RoundedCornerShape(200.dp)),
            process = if (viewModel.timerIsActive()) state.autoClickProgress else 1f,
            progressColor = buttonColor,
            backgroundColor = Orange2,
            onClick = {
                viewModel.startAutoplay()
            },
            onFocus = {
                buttonColor = primaryVariant
            },
            onFocusLeave = {
                buttonColor = secondaryVariant
                if (state.isDraw)
                    viewModel.stopTimer()
            }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    painter = painterResource(R.drawable.play_icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = stringResource(id = R.string.autoplay_menu_button),
                    color = Color.White,
                    modifier = Modifier
                        .alpha(if (it) 1f else 0.5f),
                    fontFamily = robotoFontFamily(),
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (state.screenList.isEmpty())
            Text(text = stringResource(id = R.string.autoplay_zero_select))
    }
    LaunchedEffect(Unit) {
        viewModel.onDraw()
        buttonFocusRequester.requestFocus()
    }
    LaunchedEffect(Unit) {
        if (startAutoplat)
            viewModel.startAutoplay()
    }
}