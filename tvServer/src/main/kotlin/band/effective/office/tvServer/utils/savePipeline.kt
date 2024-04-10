package band.effective.office.tvServer.utils

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

suspend fun PipelineContext<Unit, ApplicationCall>.savePipeline(
    pipeline: suspend () -> Unit
) {
    try {
        pipeline()
    } catch (e: Exception) {
        call.respondText(text = e.message ?: "error", status = HttpStatusCode.InternalServerError)
    }
}