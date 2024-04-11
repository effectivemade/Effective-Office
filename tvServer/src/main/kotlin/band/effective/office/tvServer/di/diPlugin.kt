package band.effective.office.tvServer.di

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.di() {
    install(Koin) {
        modules(dataModule, domainModule, presentationModule)
    }
}