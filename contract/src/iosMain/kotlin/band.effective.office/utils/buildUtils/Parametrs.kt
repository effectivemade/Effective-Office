package band.effective.office.utils.buildUtils

actual fun params(): BuildType = if (Platform.isDebugBinary) BuildType.DEBUG else BuildType.RELEASE