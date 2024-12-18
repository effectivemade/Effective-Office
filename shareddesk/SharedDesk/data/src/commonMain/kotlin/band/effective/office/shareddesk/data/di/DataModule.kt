package band.effective.office.shareddesk.data.di

import band.effective.office.shareddesk.data.DataSource
import band.effective.office.shareddesk.data.ktorClient
import band.effective.office.shareddesk.data.remote.RemoteDataSource
import org.koin.dsl.module

val dataModule = module {
    single<DataSource> { RemoteDataSource(ktorClient) }
}