package band.effective.office.elevator.expects

import platform.UIKit.UIPasteboard
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc

actual fun setClipboardText(text: String, label: String) {
    UIPasteboard.generalPasteboard.string = text
}

