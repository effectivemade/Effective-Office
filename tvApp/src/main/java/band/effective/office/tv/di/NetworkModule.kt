package band.effective.office.tv.di

import android.content.Context
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.*
import band.effective.office.tv.core.network.UnsafeOkHttpClient
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.errorNetwork.ErrorNetworkResponse
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.error.SynologyApiError
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
}