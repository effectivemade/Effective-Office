package band.effective.office.tv.screen.menu

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.screen.menu.component.ButtonAutoplay
import band.effective.office.tv.screen.menu.component.CircleWithBlur
import band.effective.office.tv.screen.menu.component.MainMenuPlaceHolder
import band.effective.office.tv.screen.menu.component.PlayButton
import band.effective.office.tv.screen.menu.component.ScreenList
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.ui.theme.EffectiveColor
import band.effective.office.tv.ui.theme.robotoFontFamily
import kotlinx.coroutines.launch

@Composable
fun MenuScreen(
    navController: NavController = rememberNavController()
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var isFocusOnMenu by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val options = listOf(MenuOption.Autoplay, MenuOption.Photo, MenuOption.Video)
    BackHandler(enabled = isFocusOnMenu) {
        isFocusOnMenu = false
    }
    ScreenList(
        modifier = Modifier.fillMaxSize(),
        isFocusOnMenu = isFocusOnMenu,
        options = options
    ) { index ->
        CircleWithBlur(xOffset = 150.dp, color = EffectiveColor.purple)
        CircleWithBlur(
            modifier = Modifier.alpha(0.8f),
            xOffset = (-150).dp,
            color = EffectiveColor.orange
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (index) {
                MenuOption.Autoplay -> {
                    Text(
                        modifier = Modifier.width(635.dp),
                        text = stringResource(R.string.welcome_string),
                        fontFamily = robotoFontFamily(),
                        fontSize = 50.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.width(400.dp),
                        text = stringResource(id = R.string.main_menu_text),
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(20.dp))
                    PlayButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(210.dp)
                            .clip(shape = RoundedCornerShape(40.dp))
                            .onFocusChanged { isFocusOnMenu = !it.isFocused }
                            .focusRequester(focusRequester),
                        text = stringResource(id = R.string.autoplay_button),
                        onFocus = { coroutineScope.launch { scrollState.animateScrollTo(0) } },
                        onClick = { navController.navigate(Screen.Autoplay.name) })
                    Spacer(Modifier.height(5.dp))
                    ButtonAutoplay(
                        modifier = Modifier
                            .height(50.dp)
                            .width(190.dp)
                            .clip(shape = RoundedCornerShape(210.dp))
                            .onFocusChanged { isFocusOnMenu = !it.isFocused },
                        onFocus = { coroutineScope.launch { scrollState.animateScrollTo(0) } },
                        onClick = { navController.navigate(Screen.AutoplayMenu.name) }) {
                        Text(
                            text = stringResource(id = R.string.autoplay_settings),
                            color = Color.White,
                            modifier = Modifier
                                .alpha(if (it) 1f else 0.5f),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }

                MenuOption.Photo -> {
                    MainMenuPlaceHolder(
                        text = "Раздел фото",
                        modifier = Modifier
                            .focusRequester(focusRequester) // Добавляем focusRequester
                            .onFocusChanged { isFocusOnMenu = !it.isFocused }
                    )
                }

                MenuOption.Video -> {
                    MainMenuPlaceHolder(
                        text = "Раздел видео",
                        modifier = Modifier
                            .focusRequester(focusRequester) // Добавляем focusRequester
                            .onFocusChanged { isFocusOnMenu = !it.isFocused }
                    )
                }
            }
        }
        LaunchedEffect(isFocusOnMenu) {
            if (!isFocusOnMenu) {
                kotlinx.coroutines.delay(300)
                try {
                    focusRequester.requestFocus()
                } catch (e: Exception) {
                    println("Ошибка при запросе фокуса: ${e.message}")
                }
            }
        }
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}
