package band.effective.office.utils.buildUtils

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun params(): BuildType = if (Platform.isDebugBinary) BuildType.DEBUG else BuildType.RELEASE