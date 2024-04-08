package band.effective.office.elevator.data.di

import band.effective.office.elevator.Database
import band.effective.office.elevator.data.NetworkClient
import band.effective.office.elevator.data.database.DBSource
import band.effective.office.elevator.data.database.DBSourceImpl
import band.effective.office.network.api.Api
import band.effective.office.network.api.impl.ApiImpl
//import band.effective.office.network.api.impl.ApiMock
import band.effective.office.utils.MockFactory
import org.koin.dsl.module

internal val dataModuleDI = module {
    single { NetworkClient(enableLogging = true) }

    factory { Database(get()) } // SQL Driver injected by native platforms(see: androidMain or iosMain)

    single<DBSource> { DBSourceImpl(get()) }

//    single<Api> { ApiMock(
//        realApi = ApiImpl(),
//        mockFactory = MockFactory()
//    ) }

    single<Api> { ApiImpl() }
}
