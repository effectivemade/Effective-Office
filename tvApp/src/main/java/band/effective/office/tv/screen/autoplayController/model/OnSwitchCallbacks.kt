package band.effective.office.tv.screen.autoplayController.model

interface OnSwitchCallbacks {
    fun onForwardSwitch(controllerState: AutoplayState)
    fun onBackSwitch(controllerState: AutoplayState)
    fun onLeave()
}