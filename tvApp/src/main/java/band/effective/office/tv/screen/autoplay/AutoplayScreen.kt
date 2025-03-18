package band.effective.office.tv.screen.autoplay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.screen.error.ErrorScreen
import band.effective.office.tv.screen.leaderIdEvents.LeaderIdEventsScreen
import band.effective.office.tv.screen.load.LoadScreen
import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.screen.photo.BestPhotoScreen

@Composable
fun getScreen(screenName: Screen) = when (screenName) {
    Screen.Events -> @Composable {
        LeaderIdEventsScreen()
    }
    Screen.BestPhoto -> @Composable {
        BestPhotoScreen()
    }
    else -> {}
}

@Composable
fun AutoplayScreen(viewModel: AutoplayViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> LoadScreen()
        state.isError -> ErrorScreen(state.errorMessage.asString())
        state.isLoaded -> getScreen(screenName = state.currentScreen)
    }
}