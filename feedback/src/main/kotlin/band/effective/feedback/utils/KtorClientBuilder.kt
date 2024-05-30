package band.effective.feedback.utils

import band.effective.feedback.data.mattermost.NotFoundException
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorClientBuilder {
    private var token: String? = null
    fun setToken(token: String): KtorClientBuilder {
        this.token = token
        return this
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.negotiation() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                encodeDefaults = true
                classDiscriminator = "classDiscriminator"
            })
        }
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.logging() {
        install(Logging) {
            level = LogLevel.BODY
            logger = Logger.SIMPLE
        }
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.auth(token: String) {
        install(Auth) {
            bearer {
                loadTokens { BearerTokens(token, "") }
            }
        }
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.notFoundValidator() {
        HttpResponseValidator {
            handleResponseExceptionWithRequest { cause, request ->
                val clientException = cause as? ClientRequestException ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                if (exceptionResponse.status == HttpStatusCode.NotFound) {
                    throw NotFoundException()
                }
            }
        }
    }

    fun build() = HttpClient(CIO) {
        negotiation()
        logging()
        notFoundValidator()
        if (token != null) {
            auth(token!!)
        }
    }
}

fun KtorClientBuilder(builder: KtorClientBuilder.() -> Unit): HttpClient =
    with(KtorClientBuilder()) {
        builder()
        build()
    }