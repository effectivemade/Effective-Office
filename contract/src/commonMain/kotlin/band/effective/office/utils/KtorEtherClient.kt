package band.effective.office.utils

import band.effective.office.network.createHttpEngine
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import effective_office.contract.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

object KtorEtherClient {
    /**token for authorization*/
    var token = mutableListOf<String>(BuildConfig.apiKey)
    /**default http client with KtorEtherClient*/
    val httpClient by lazy {

        createHttpEngine().config {
            install(KtorEitherPlugin)
            install(Auth) {
                bearer {
                    loadTokens {
                        println("used token = ${token.last()}")
                        BearerTokens(token.last(), "")
                    }
                    refreshTokens {
                        println("calling refresh")
                        println("refreshed token = ${token.last()}")
                        BearerTokens(token.last(), "")
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 100000
                connectTimeoutMillis = 100000
            }
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    val avatarHttpEngine by lazy {
        createHttpEngine().config {
            install(KtorEitherPlugin)
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
                connectTimeoutMillis = 10000
            }
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    enum class RestMethod { Get, Post, Delete, Put }

    /**Safety response
     * @param urlString request url
     * @param method request rest method, default GET
     * @param client ktor http client, default httpClient
     * @param block ktor http request builder scope*/
    suspend inline fun <reified T> securityResponse(
        urlString: String,
        method: RestMethod = RestMethod.Get,
        client: HttpClient = httpClient,
        block: HttpRequestBuilder.() -> Unit = {},
    ): Either<ErrorResponse, T> =
        try {
            when (method) {
                RestMethod.Get -> client.get(urlString, block)
                RestMethod.Post -> client.post(urlString, block)
                RestMethod.Delete -> client.delete(urlString, block)
                RestMethod.Put -> client.put(urlString, block)
            }.body()
        } catch (e: Exception) {
            Either.Error(ErrorResponse(code = 0, description = e.message ?: "Error"))
        }
}