package band.effective.office.tv.core.ui.screen_with_controls.model

import kotlinx.coroutines.flow.MutableStateFlow

data class MenuState(
    val isVisible: Boolean,
    val selectButton: MenuButton
) {
    companion object {
        var state = MutableStateFlow(MenuState(false, MenuButton.Nothink))
    }
}