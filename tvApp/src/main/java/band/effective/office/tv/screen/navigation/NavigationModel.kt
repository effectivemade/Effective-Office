package band.effective.office.tv.screen.navigation

import androidx.compose.runtime.Composable
import band.effective.office.tv.screen.history.HistoryScreen
import band.effective.office.tv.screen.leaderIdEvents.LeaderIdEventsScreen
import band.effective.office.tv.screen.photo.BestPhotoScreen

enum class Screen {
    Menu, Events, BestPhoto, History, Autoplay, AutoplayMenu
}

data class NavigationModel(
    val screen: Screen, val screenFun: @Composable () -> Unit, val title: String
) {
    companion object {
        val screensList = listOf(
            NavigationModel(Screen.Events, @Composable { LeaderIdEventsScreen() }, "Events"),
            NavigationModel(Screen.BestPhoto, @Composable { BestPhotoScreen() }, "Best Photo"),
            NavigationModel(Screen.History, @Composable { HistoryScreen() }, "History")
        )
    }
}