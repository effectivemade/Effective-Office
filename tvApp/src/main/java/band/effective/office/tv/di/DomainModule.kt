package band.effective.office.tv.di

import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.impl.MattermostWebSocketClientImpl
import band.effective.office.tv.repository.clockify.ClockifyRepository
import band.effective.office.tv.repository.clockify.impl.ClockifyRepositoryImpl
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import band.effective.office.tv.repository.duolingo.impl.DuolingoRepositoryImpl
import band.effective.office.tv.repository.leaderId.LeaderIdEventsInfoRepository
import band.effective.office.tv.repository.leaderId.impl.LeaderIdEventsInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Singleton
    @Binds
    fun provideLeaderIdEventsInfoRepository(leaderIdEventsInfoRepositoryImpl: LeaderIdEventsInfoRepositoryImpl): LeaderIdEventsInfoRepository

    @Singleton
    @Binds
    fun provideMattermostClient(
        mattermostWebSocketClientImpl: MattermostWebSocketClientImpl
    ): MattermostWebSocketClient

    @Singleton
    @Binds
    fun provideDuolingoRepository(duolingoRepositoryImpl: DuolingoRepositoryImpl): DuolingoRepository

    @Singleton
    @Binds
    fun provideClockifyRepository(
        clockifyRepositoryImpl: ClockifyRepositoryImpl
    ): ClockifyRepository
}