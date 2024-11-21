package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.Settings

/**use case for get settings values*/
class CheckSettingsUseCase {
    /**Get current room from settings*/
    operator fun invoke() =
         Settings.current.checkCurrentRoom()
}