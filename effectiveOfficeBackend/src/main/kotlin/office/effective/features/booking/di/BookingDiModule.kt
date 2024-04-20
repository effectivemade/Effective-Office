package office.effective.features.booking.di

import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.booking.facade.BookingFacade
import office.effective.features.booking.repository.BookingMeetingRepository
import office.effective.features.booking.service.BookingService
import office.effective.features.booking.converters.GoogleCalendarConverter
import office.effective.features.booking.repository.BookingRegularRepository
import office.effective.serviceapi.IBookingService
import org.koin.dsl.module

val bookingDiModule = module(createdAtStart = true) {
    single { BookingRepositoryConverter(get(), get(), get(),  get()) }
    single { GoogleCalendarConverter(get(), get(), get(), get(), get(), get(), get()) }
    single { BookingRegularRepository(get(), get(), get(), get()) }
    single { BookingMeetingRepository(get(), get(), get(), get()) }
    single<IBookingService> { BookingService(get(), get(), get(), get()) }
    single { BookingFacadeConverter(get(), get()) }
    single { BookingFacade(get(), get(), get(), get()) }
}
