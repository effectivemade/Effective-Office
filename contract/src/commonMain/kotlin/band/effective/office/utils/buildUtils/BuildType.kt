package band.effective.office.utils.buildUtils

import effective_office.contract.BuildConfig

enum class BuildType(val serverUrl: String) {
    RELEASE(BuildConfig.releaseServerUrl),
    DEBUG(BuildConfig.debugServerUrl)
}