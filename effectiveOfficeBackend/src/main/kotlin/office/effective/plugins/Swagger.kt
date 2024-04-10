package office.effective.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*

fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = true
        }
        info {
            title = "Effective Office API"
            version = "0.0.1"
            description = "API for EffectiveOffice applications."
        }
        server {
            url = "http://0.0.0.0:8080"
            description = "Development Server"
        }
    }
}
