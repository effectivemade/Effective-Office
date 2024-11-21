package band.effective.office.elevator.ui.models

import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class UserData
    (val title: StringResource,
     val icon: ImageResource) {
    Phone(
        icon = MainRes.images.icon_call,
        title = MainRes.strings.phone
    ),
    Telegram(
        icon = MainRes.images.icon_telegram,
        title = MainRes.strings.telegram,
    )
}
inline fun <reified : Enum<UserData>> getAllUserDataProfile(): Array<UserData> {
    return (enumValues())
}