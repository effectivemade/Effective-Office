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
import office.effective.features.user.facade.UserFacadeV1
import office.effective.features.user.routes.swagger.updateUserV1
import office.effective.features.user.routes.swagger.returnUserByIdV1
import office.effective.features.user.routes.swagger.returnUsersV1
import org.koin.core.context.GlobalContext

fun Route.userRoutingV1() {
    val facade: UserFacadeV1 = GlobalContext.get().get()

    route("/api/v1/users", {}) {
        get(SwaggerDocument.returnUsersV1()) {
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
        get("/{user_id}", SwaggerDocument.returnUserByIdV1()) {
            val userId: String = call.parameters["user_id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = facade.getUserById(userId)
            call.respond(user)
        }
        put("/{user_id}", SwaggerDocument.updateUserV1()) {
            val user: UserDTO = call.receive<UserDTO>()
            call.respond(facade.updateUser(user))
        }
    }
}