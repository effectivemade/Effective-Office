package office.effective.features.workspace.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.workspace.facade.WorkspaceFacade
import office.effective.features.workspace.routes.swagger.returnAllZones
import office.effective.features.workspace.routes.swagger.returnWorkspaceById
import office.effective.features.workspace.routes.swagger.returnWorkspaceByTag
import org.koin.core.context.GlobalContext

fun Route.workspaceRouting() {
    route("/workspaces") {
        val facade: WorkspaceFacade = GlobalContext.get().get()

        get("/{id}", SwaggerDocument.returnWorkspaceById()) {
            val id: String = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(facade.findById(id))
        }
        get(SwaggerDocument.returnWorkspaceByTag()) {
            val tag: String = call.request.queryParameters["workspace_tag"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            val freeFrom: String? = call.request.queryParameters["free_from"]
            val freeUntil: String? = call.request.queryParameters["free_until"]

            if(freeFrom == null && freeUntil == null) {
                call.respond(facade.findAllByTag(tag))
            } else {
                val fromLong: Long = if (freeFrom==null) 0
                else freeFrom.toLongOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)

                val untilLong: Long = if (freeUntil==null) 2147483647000L
                else freeUntil.toLongOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)

                call.respond(facade.findAllFreeByPeriod(tag, fromLong, untilLong))
            }
        }
        get("/zones", SwaggerDocument.returnAllZones()) {
            call.respond(facade.findAllZones())
        }
    }
}
