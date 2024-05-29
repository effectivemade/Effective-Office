package band.effective.office.elevator.expects

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import band.effective.office.elevator.AndroidApp
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.desc


actual fun setClipboardText(text: String, label: String, toastMessage: StringResource) {
    val context = AndroidApp.INSTANCE

    val clip = ClipData.newPlainText(label, text)
    val clipboardService =  context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardService.setPrimaryClip(clip)
    showToast(toastMessage.desc().toString(context))
}