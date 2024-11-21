package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.Settings

/**Use case for set settings*/
class SetRoomUseCase {
    /**save current room name*/
    operator fun invoke(nameRoom: String) =
        Settings.current.updateSettings(nameRoom)
}