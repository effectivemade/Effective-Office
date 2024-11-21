package band.effective.office.tv.di

import band.effective.office.tv.network.notion.EmployeeInfoRemoteDataSource
import band.effective.office.tv.network.notion.EmployeeInfoRemoteDataSourceImpl
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import band.effective.office.tv.repository.notion.impl.EmployeeInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface EmployeeInfoModule {
    @Singleton
    @Binds
    fun bindEmployeeInfoRepository(impl: EmployeeInfoRepositoryImpl): EmployeeInfoRepository

    @Singleton
    @Binds
    fun bindEmployeeInfoRemoteDataSource(impl: EmployeeInfoRemoteDataSourceImpl): EmployeeInfoRemoteDataSource
}