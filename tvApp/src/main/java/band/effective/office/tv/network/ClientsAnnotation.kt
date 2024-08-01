package band.effective.office.tv.network

import javax.inject.Qualifier


//this annotations for retrofit
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LeaderIdRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SynologyRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MattermostClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UselessFactClient


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DualingoRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnsafeOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ClockifyClient