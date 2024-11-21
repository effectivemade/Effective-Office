package band.effective.office.tv.screen.autoplayController.model

data class ScreenState(
    val isPlay: Boolean,
    val isForwardDirection: Boolean
) {
    companion object {
        val default = ScreenState(isPlay = true, isForwardDirection = true)
    }
}
