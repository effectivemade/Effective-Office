package band.effective.office.utils.buildUtils

import band.effective.office.contract.BuildConfig


actual fun params(): BuildType = if (BuildConfig.DEBUG) BuildType.DEBUG else BuildType.RELEASE