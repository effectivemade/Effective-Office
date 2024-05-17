package office.effective.plugins

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import office.effective.common.exception.*
import org.slf4j.LoggerFactory

fun Application.configureExceptionHandling() {
    val logger = LoggerFactory.getLogger(this::class.java)
    install(StatusPages) {
        exception<InstanceNotFoundException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "404: $cause", status = HttpStatusCode.NotFound)
        }
        exception<BadRequestException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "400: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<ValidationException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "400: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<WorkspaceUnavailableException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "400: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<RequestValidationException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "400: ${cause.reasons.joinToString()}", status = HttpStatusCode.BadRequest)
        }
        exception<UnavailableOperationException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "400: $cause", status = HttpStatusCode.BadRequest)
        }
        exception<GoogleJsonResponseException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(
                text = "${cause.statusCode}: ${cause.message}",
                status = HttpStatusCode.fromValue(cause.statusCode)
            )
        }
        exception<MissingIdException> { call, cause ->
            logger.info("Exception handled: ", cause)
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<Throwable> { call, cause ->
            logger.error("Unhandled exception: ", cause)
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
}