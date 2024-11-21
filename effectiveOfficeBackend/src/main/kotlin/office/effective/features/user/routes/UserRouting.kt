package office.effective.features.user.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.put
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.UserDTO
import office.effective.features.user.facade.UserFacade
import office.effective.features.user.routes.swagger.updateUser
import office.effective.features.user.routes.swagger.returnUserById
import office.effective.features.user.routes.swagger.returnUsers
import org.koin.core.context.GlobalContext

fun Route.userRouting() {
    val facade: UserFacade = GlobalContext.get().get()

    route("users", {}) {
        get(SwaggerDocument.returnUsers()) {
            val tag: String? = call.request.queryParameters["user_tag"]
            val email: String? = call.request.queryParameters["email"]

            when {
                (email != null && tag != null) -> {
                    call.response.status(HttpStatusCode.BadRequest)
                    call.respondText("email and tag are mutually exclusive parameters")
                }
                (email != null) -> call.respond(facade.getUserByEmail(email))
                (tag != null) -> call.respond(facade.getUsersByTag(tag))
                else -> call.respond(facade.getUsers())
            }
        }
        get("/{user_id}", SwaggerDocument.returnUserById()) {
            val userId: String = call.parameters["user_id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = facade.getUserById(userId)
            call.respond(user)
        }
        put("/{user_id}", SwaggerDocument.updateUser()) {
            val user: UserDTO = call.receive<UserDTO>()
            call.respond(facade.updateUser(user))
        }
    }
}