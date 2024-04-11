package band.effective.office.tvServer.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun defaultHttpClient(bearerToken: String? = null) = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }
    install(Logging) {
        level = LogLevel.BODY
        logger = Logger.SIMPLE
    }
    if (bearerToken != null) {
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(bearerToken, "")
                }
            }
        }
    }
}