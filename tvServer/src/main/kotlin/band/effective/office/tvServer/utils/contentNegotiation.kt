package band.effective.office.tvServer.utils

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.contentNegotiation() {
    install(ContentNegotiation) {
        json()
    }
}