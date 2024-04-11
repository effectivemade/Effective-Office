package office.effective.features.booking.routes

import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.BookingDTO
import office.effective.features.booking.facade.BookingFacadeV1
import office.effective.features.booking.routes.swagger.*
import org.koin.core.context.GlobalContext

fun Route.bookingRoutingV1() {
    route("/v1/bookings") {
        val bookingFacade: BookingFacadeV1 = GlobalContext.get().get()

        get("/{id}", SwaggerDocument.returnBookingByIdV1()) {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(bookingFacade.findById(id))
        }

        get(SwaggerDocument.returnBookingsV1()) {
            val userId: String? = call.request.queryParameters["user_id"]
            val workspaceId: String? = call.request.queryParameters["workspace_id"]
            val bookingRangeTo: Long? = call.request.queryParameters["range_to"]?.let {
                it.toLongOrNull()
                    ?: throw BadRequestException("range_to can't be parsed to Long")
            }
            call.request.queryParameters["range_from"]?.let {
                val bookingRangeFrom: Long = it.toLongOrNull()
                    ?: throw BadRequestException("range_from can't be parsed to Long")
                call.respond(bookingFacade.findAll(userId, workspaceId, bookingRangeTo, bookingRangeFrom))
                return@get
            }
            call.respond(bookingFacade.findAll(userId, workspaceId, bookingRangeTo))
        }
        post(SwaggerDocument.postBookingV1()) {
            val dto = call.receive<BookingDTO>()

            call.response.status(HttpStatusCode.Created)
            val result = bookingFacade.post(dto)
            call.respond(result)
        }
        put(SwaggerDocument.putBookingV1()) {
            val dto = call.receive<BookingDTO>()

            val result = bookingFacade.put(dto)
            call.respond(result)
        }
        delete("{id}", SwaggerDocument.deleteBookingByIdV1()) {
            val id: String = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest)

            bookingFacade.deleteById(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}