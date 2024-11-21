package band.effective.office.elevator.expects

import band.effective.office.elevator.ui.uiViewController
import com.seiko.imageloader.component.ComponentRegistryBuilder
import com.seiko.imageloader.component.setupDefaultComponents
import io.github.aakira.napier.Napier
import okio.Path
import okio.Path.Companion.toPath
import platform.UIKit.UIAlertController
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.Foundation.*

actual fun showToast(message: String) {
    Napier.e { message }
    val controller = UIAlertController(null, null)
    controller.message = message
    controller.showViewController(uiViewController, null)
}

actual fun generateVibration(milliseconds: Long) {
//    AudioServicesPlaySystemSound(kSystemSoundID_Vibrate)
}

actual fun makeCall(phoneNumber: String) {
    val url = NSURL(string = "tel:$phoneNumber")
    UIApplication.sharedApplication.openURL(url)
}

actual fun pickTelegram(telegramNick: String) {
    val urlString = "https://telegram.me/$telegramNick"
    val url = NSURL.URLWithString(urlString)
    url?.let{
        val application = UIApplication.sharedApplication
        application.openURL(url)
    }
}

actual fun pickSBP(phoneNumber: String) {
    val url = NSURL(string = "tel:$phoneNumber")
    UIApplication.sharedApplication.openURL(url)
}

actual fun ComponentRegistryBuilder.setupDefaultComponents() = this.setupDefaultComponents()

actual fun getImageCacheDirectoryPath(): Path {
    val cacheDir = NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true
    ).first() as String
    return ("$cacheDir/media").toPath()
}