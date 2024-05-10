package band.effective.feedback.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class KtorClientBuilder {
    private var token: String? = null
    fun setToken(token: String): KtorClientBuilder {
        this.token = token
        return this
    }

    fun build() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
            logger = Logger.SIMPLE
        }
        if (token != null) {
            install(Auth) {
                bearer {
                    loadTokens { BearerTokens(token!!, "") }
                }
            }
        }
    }
}

fun KtorClientBuilder(builder: KtorClientBuilder.() -> Unit): HttpClient =
    with(KtorClientBuilder()) {
        builder()
        build()
    }