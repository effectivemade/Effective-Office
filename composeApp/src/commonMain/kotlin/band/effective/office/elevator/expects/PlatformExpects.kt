package band.effective.office.elevator.expects

import com.seiko.imageloader.component.ComponentRegistryBuilder
import okio.Path

expect fun showToast(message: String)

expect fun generateVibration(milliseconds: Long)

expect fun makeCall(phoneNumber: String)

expect fun pickTelegram(telegramNick: String)

expect fun pickSBP(phoneNumber: String)

internal expect fun ComponentRegistryBuilder.setupDefaultComponents()

internal expect fun getImageCacheDirectoryPath(): Path