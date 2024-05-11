package band.effective.feedback.presentation

import band.effective.feedback.presentation.di.dataModule
import band.effective.feedback.presentation.di.domainModule
import band.effective.feedback.presentation.di.presentationModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin

fun Application.modules() {
    di()
    negotiation()
    routes()
}

fun Application.di() {
    install(Koin) {
        modules(dataModule(), domainModule(), presentationModule())
    }
}

fun Application.routes() {
    install(Routing) {
        mattermostWebhookRoutes()
    }
}

fun Application.negotiation() {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}