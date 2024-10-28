package band.effective.office.elevator.expects

actual fun setClipboardText(text: String, label: String) {
    UIPasteboard.general.string = text
}
