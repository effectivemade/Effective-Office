package band.effective.office.tv.di

import android.content.Context
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.*
import band.effective.office.tv.core.network.UnsafeOkHttpClient
import band.effective.office.tv.network.*
import band.effective.office.tv.network.clockify.ApiKeyInterceptor
import band.effective.office.tv.network.clockify.ClockifyApi
import band.effective.office.tv.network.clockify.models.error.ClockifyApiError
import band.effective.office.tv.network.duolingo.DuolingoApi
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.errorNetwork.ErrorNetworkResponse
import band.effective.office.tv.network.mattermost.MattermostApi
import band.effective.office.tv.network.mattermost.model.MattermostErrorResponse
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.error.SynologyApiError
import band.effective.office.tv.network.uselessFact.UselessFactApi
import band.effective.office.tv.repository.supernova.SupernovaRepository
import band.effective.office.tv.repository.supernova.SupernovaRepositoryImpl
import band.effective.office.tv.utils.GregorianCalendarMoshiAdapter
import band.effective.office.tv.utils.RStringGetter
import band.effective.office.workTogether.WorkTogether
import band.effective.office.workTogether.WorkTogetherImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.addAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import notion.api.v1.NotionClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @OptIn(ExperimentalStdlibApi::class)
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().addAdapter(GregorianCalendarMoshiAdapter()).build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()


    @Singleton
    @Provides
    @band.effective.office.tv.network.UnsafeOkHttpClient
    fun provideUnsafeOkHttpClient() =
        UnsafeOkHttpClient.getUnsafeOkHttpClient().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    @Singleton
    @Provides
    @MattermostClient
    fun provideMattermostOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.mattermostBotToken))
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
            .build()

    @Singleton
    @Provides
    @ClockifyClient
    fun provideClockifyOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.clockifyApiKey))
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            .build()

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi).asLenient()

    @Singleton
    @Provides
    fun provideLeaderApi(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient
    ): LeaderApi = Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(EitherCallAdapterFactory(ErrorNetworkResponse::class.java))
            .client(client)
            .baseUrl(BuildConfig.apiLeaderUrl)
            .build()
            .create()

    @Singleton
    @Provides
    fun provideApiSynology(
        moshiConverterFactory: MoshiConverterFactory,
        @band.effective.office.tv.network.UnsafeOkHttpClient client: OkHttpClient,
    ) = Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(EitherCallAdapterFactory(SynologyApiError::class.java))
            .client(client)
            .baseUrl(BuildConfig.apiSynologyUrl)
            .build()
            .create(SynologyApi::class.java)

    @Singleton
    @Provides
    fun provideApiMattermost(
        moshiConverterFactory: MoshiConverterFactory,
        @MattermostClient client: OkHttpClient,
    ): MattermostApi =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(
                EitherCallAdapterFactory(MattermostErrorResponse::class.java)
            )
            .client(client)
            .baseUrl("https://${BuildConfig.apiMattermostUrl}")
            .build()
            .create()

    @Singleton
    @Provides
    fun provideUselessFactApi(
        moshiConverterFactory: MoshiConverterFactory,
        @UselessFactClient client: OkHttpClient,
    ): UselessFactApi =
        Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(EitherCallAdapterFactory(ErrorNetworkResponse::class.java))
            .client(client)
            .baseUrl(BuildConfig.uselessFactsApi).build().create()

    @Singleton
    @Provides
    fun provideApiDuolingo(
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient,
    ) = Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(EitherCallAdapterFactory(SynologyApiError::class.java))
            .client(client)
            .baseUrl(BuildConfig.duolingoUrl)
            .build()
            .create(DuolingoApi::class.java)

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Singleton
    @Provides
    fun provideNotionClient(): NotionClient {
        return NotionClient(BuildConfig.notionToken)
    }

    @Singleton
    @Provides
    fun provideRStringGetter(@ApplicationContext appContext: Context): RStringGetter =
        RStringGetter(context = appContext)

    @Singleton
    @Provides
    fun provideWorkTogether(notionClient: NotionClient): WorkTogether =
        WorkTogetherImpl(notionClient, BuildConfig.notionDatabaseId)

    @Singleton
    @Provides
    fun provideSupernova(notionClient: NotionClient): SupernovaRepository =
        SupernovaRepositoryImpl(notionClient)

    @Singleton
    @Provides
    fun provideClockifyApi(
        moshiConverterFactory: MoshiConverterFactory,
        @ClockifyClient client: OkHttpClient
    ) = Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(
                EitherCallAdapterFactory(ClockifyApiError::class.java)
            )
            .client(client)
            .baseUrl(BuildConfig.clockifyUrl)
            .build()
            .create(ClockifyApi::class.java)
}