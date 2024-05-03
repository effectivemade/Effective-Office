package band.effective.core

import band.effective.MattermostSettings
import band.effective.SynologySettings
import band.effective.mattermost.MattermostApi
import band.effective.mattermost.models.response.adapter.ChannelsAdapter
import band.effective.mattermost.models.response.adapter.EitherMattermostAdapterFactory
import band.effective.mattermost.models.response.adapter.PostsAdapter
import band.effective.synology.SynologyApi
import band.effective.utils.getEnv
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .add(PostsAdapter())
        .add(ChannelsAdapter())
        .build()

val moshiConverterFactory = MoshiConverterFactory.create(moshi).asLenient()

val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HEICToPNGInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

val unsafeOkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }).build()

val mattermostEitherFactory = EitherMattermostAdapterFactory()

val mattermostApi = Retrofit.Builder()
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(mattermostEitherFactory)
        .client(okHttpClient)
        .baseUrl(getEnv(MattermostSettings.baseURL))
        .build()
        .create(MattermostApi::class.java)

val synologyApi = Retrofit.Builder()
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(mattermostEitherFactory)
        .client(unsafeOkHttpClient)
        .baseUrl(getEnv(SynologySettings.baseURL))
        .build()
        .create(SynologyApi::class.java)
