package band.effective.office.tablet.di

import band.effective.office.tablet.domain.useCase.OrganizersInfoUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.domain.useCase.SelectRoomUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.LocalBookingRepository
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.network.repository.impl.LocalEventStoreRepository
import band.effective.office.tablet.network.repository.impl.NetworkEventRepository
import band.effective.office.tablet.network.repository.impl.OrganizerRepositoryImpl
import band.effective.office.tablet.network.repository.impl.EventManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    single<OrganizerRepository> { OrganizerRepositoryImpl(api = get()) }
    singleOf(::NetworkEventRepository) bind BookingRepository::class
    singleOf(::LocalEventStoreRepository) bind LocalBookingRepository::class
    singleOf(::EventManager)

    single<RoomInfoUseCase> { RoomInfoUseCase(eventManager = get()) }
    single<OrganizersInfoUseCase> { OrganizersInfoUseCase(repository = get()) }
    single<TimerUseCase> { TimerUseCase() }
    single<SelectRoomUseCase> { SelectRoomUseCase() }
}