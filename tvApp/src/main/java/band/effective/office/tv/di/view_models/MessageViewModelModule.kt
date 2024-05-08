package band.effective.office.tv.di.view_models

import android.content.Context
import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.impl.MattermostBot
import band.effective.office.tv.network.MattermostClient
import band.effective.office.tv.repository.uselessFactRepository.UselessFactRepository
import band.effective.office.tv.repository.uselessFactRepository.impl.UselessFactRepositoryImpl
import coil.ImageLoader
import com.github.awxkee.avifcoil.decoder.HeifDecoder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
interface MessageViewModelBindsModule {
    @ViewModelScoped
    @Binds
    fun bindsUselessFactRepository(repository: UselessFactRepositoryImpl): UselessFactRepository

    @ViewModelScoped
    @Binds
    fun bindsMessengerBot(mattermostBot: MattermostBot): MessengerBot
}

@Module
@InstallIn(ViewModelComponent::class)
class MessageViewModelModule {
    @ViewModelScoped
    @Provides
    @MattermostClient
    fun provideImageLoader(
        @ApplicationContext context: Context,
        @MattermostClient okHttpClient: OkHttpClient
    ): ImageLoader =
        ImageLoader
            .Builder(context)
            .components {
                add(HeifDecoder.Factory(context))
            }
            .okHttpClient(okHttpClient)
            .build()
}