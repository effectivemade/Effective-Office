package band.effective.office.tv.screen.autoplay

import band.effective.office.tv.screen.navigation.Screen
import band.effective.office.tv.utils.StringResource

data class AutoplayUiState(
    val isLoading: Boolean,
    val isLoaded: Boolean,
    val isError: Boolean,
    val errorMessage: StringResource,
    val currentScreen: Screen
) {
    companion object {
        val defaultState = AutoplayUiState(
            isLoading = true,
            isLoaded = false,
            isError = false,
            errorMessage = StringResource.DynamicResource("Error"),
            currentScreen = Screen.Autoplay
        )
    }
}