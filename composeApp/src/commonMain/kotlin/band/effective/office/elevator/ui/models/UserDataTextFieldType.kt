package band.effective.office.elevator.ui.models

import band.effective.office.elevator.MainRes
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class UserDataTextFieldType
    (
    val title: StringResource,
    val icon: ImageResource,
    val placeholder: StringResource
    ) {
    Person(
        icon = MainRes.images.person_ic,
        title = MainRes.strings.last_name_and_first_name,
        placeholder = MainRes.strings.profile_hint
    ),
    Post(
        icon = MainRes.images.symbols_work,
        title = MainRes.strings.post,
        placeholder = MainRes.strings.profile_hint_
    ),
    Phone(
        icon = MainRes.images.mask_number,
        title = MainRes.strings.phone_number,
        placeholder = MainRes.strings.number_hint
    ),
    Telegram(
        icon = MainRes.images.mask_commercial_at,
        title = MainRes.strings.telegram,
        placeholder = MainRes.strings.employee_hint
    )
}
inline fun <reified : Enum<UserDataTextFieldType>> getAllUserDataEditProfile(): Array<UserDataTextFieldType> {
    return (enumValues())
}