package band.effective.office.elevator.expects

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc

actual fun setClipboardText(text: String, label: String, toastMessage: StringResource) {
    UIPasteboard.general.string = text
    showToast(toastMessage.desc().localized())
}