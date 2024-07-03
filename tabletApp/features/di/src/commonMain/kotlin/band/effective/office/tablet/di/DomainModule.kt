package band.effective.office.tablet.di

import band.effective.office.tablet.domain.useCase.BookingUseCase
import band.effective.office.tablet.domain.useCase.OrganizersInfoUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.domain.useCase.SelectRoomUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.network.repository.impl.LocalStoreRepository
import band.effective.office.tablet.network.repository.impl.NetworkRepository
import band.effective.office.tablet.network.repository.impl.OrganizerRepositoryImpl
import band.effective.office.tablet.network.repository.impl.StateManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    single<OrganizerRepository> { OrganizerRepositoryImpl(api = get()) }
    singleOf(::NetworkRepository)
    singleOf(::LocalStoreRepository)
    singleOf(::StateManager)

    single<RoomInfoUseCase> { RoomInfoUseCase(repository = get()) }
    single<OrganizersInfoUseCase> { OrganizersInfoUseCase(repository = get()) }
    single<BookingUseCase> { BookingUseCase(repository = get()) }
    single<TimerUseCase> { TimerUseCase() }
    single<SelectRoomUseCase> { SelectRoomUseCase() }
}