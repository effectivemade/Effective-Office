package band.effective.office.shareddesk.interactivemap.utils

enum class Platform {
    MOBILE,
    PC,
}

expect fun getPlatform(): Platform