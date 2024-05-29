package band.effective.office.tablet.di

import band.effective.office.tablet.domain.useCase.BookingUseCase
import band.effective.office.tablet.domain.useCase.OrganizersInfoUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.domain.useCase.SelectRoomUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.CancelRepository
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.network.repository.impl.BookingRepositoryImpl
import band.effective.office.tablet.network.repository.impl.BufferedRoomRepository
import band.effective.office.tablet.network.repository.impl.CancelRepositoryImpl
import band.effective.office.tablet.network.repository.impl.OrganizerRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<OrganizerRepository> { OrganizerRepositoryImpl(api = get()) }
    single<CancelRepository> { CancelRepositoryImpl(api = get()) }
    single<BookingRepository> { BookingRepositoryImpl(api = get()) }
    single<BufferedRoomRepository> { BufferedRoomRepository(api = get()) }
    single<RoomRepository> { get<BufferedRoomRepository>() }

    single<RoomInfoUseCase> { RoomInfoUseCase(repository = get()) }
    single<OrganizersInfoUseCase> { OrganizersInfoUseCase(repository = get()) }
    single<BookingUseCase> { BookingUseCase(repository = get(), roomRepository = get()) }
    single<TimerUseCase> { TimerUseCase() }
    single<SelectRoomUseCase> { SelectRoomUseCase() }
}