package band.effective.office.tv.network.clockify

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor(private val key: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("x-api-key", key)
            .build()
        return chain.proceed(newRequest)
    }
}