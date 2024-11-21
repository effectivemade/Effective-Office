package band.effective.office.tv.di.view_models

import band.effective.office.tv.screen.autoplayController.AutoplayController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AutoplayViewModule {
    @Singleton
    @Provides
    fun provideAutoplayController() = AutoplayController()
}