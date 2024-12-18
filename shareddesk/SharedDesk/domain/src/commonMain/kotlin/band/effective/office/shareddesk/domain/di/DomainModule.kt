package band.effective.office.shareddesk.domain.di

import band.effective.office.shareddesk.domain.mapper.WorkplacesDomainMapper
import band.effective.office.shareddesk.domain.mapper.WorkplacesDtoMapper
import band.effective.office.shareddesk.domain.repository.OfficeSourceMapRepository
import band.effective.office.shareddesk.domain.repository.impl.OfficeSourceMapRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<OfficeSourceMapRepository> { OfficeSourceMapRepositoryImpl(get(), get(), get(), get()) }
    single { WorkplacesDomainMapper() }
    single { WorkplacesDtoMapper() }
}