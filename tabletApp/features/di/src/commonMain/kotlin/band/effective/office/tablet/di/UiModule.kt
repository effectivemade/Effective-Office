package band.effective.office.tablet.di

import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.SetRoomUseCase
import band.effective.office.tablet.domain.useCase.SlotUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
import org.koin.dsl.module

val uiModule = module {
    single<CheckSettingsUseCase> { CheckSettingsUseCase() }
    single<CheckBookingUseCase> {
        CheckBookingUseCase(
            roomInfoUseCase = get()
        )
    }
    single<SetRoomUseCase> { SetRoomUseCase() }
    single<SlotUseCase> { SlotUseCase() }
    single<UpdateUseCase> { UpdateUseCase(timerUseCase = get(), roomInfoUseCase = get()) }
}