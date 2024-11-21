package band.effective.office.tv.screen.autoplayMenu

import band.effective.office.tv.screen.navigation.Screen

data class AutoplayMenuState(
    val screenList: List<Screen>,
    val autoClickProgress: Float,
    val isDraw: Boolean
) {
    companion object {
        val defaultState = AutoplayMenuState(
            screenList = listOf(
                Screen.Stories,
                Screen.BestPhoto,
                Screen.Events
            ),
            autoClickProgress = 0f,
            isDraw = false
        )
    }
}
