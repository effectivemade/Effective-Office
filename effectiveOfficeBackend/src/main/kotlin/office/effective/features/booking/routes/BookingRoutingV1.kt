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
import office.effective.common.constants.BookingConstants
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.BookingRequestDTO
import office.effective.features.booking.facade.BookingFacadeV1
import office.effective.features.booking.routes.swagger.*
import org.koin.core.context.GlobalContext
import java.time.LocalDate
import java.time.ZoneId

fun Route.bookingRoutingV1() {
    route("/api/v1/bookings") {
        val bookingFacade: BookingFacadeV1 = GlobalContext.get().get()

        get("/{id}", SwaggerDocument.returnBookingByIdV1()) {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(bookingFacade.findById(id))
        }

        get(SwaggerDocument.returnBookingsV1()) {
            val todayEpoch = LocalDate.now()
                .atStartOfDay(ZoneId.of(BookingConstants.DEFAULT_TIMEZONE_ID))
                .toInstant()
                .toEpochMilli()
            val endOfDayEpoch = todayEpoch + 1000*60*60*24

            val userId: String? = call.request.queryParameters["user_id"]
            val workspaceId: String? = call.request.queryParameters["workspace_id"]

            val returnInstances: Boolean = call.request.queryParameters["return_instances"]?.let { stringRangeTo ->
                stringRangeTo.toBooleanStrictOrNull()
                    ?: throw BadRequestException("return_instances can't be parsed to Boolean")
            } ?: true
            val bookingRangeTo: Long = call.request.queryParameters["range_to"]?.let { stringRangeTo ->
                stringRangeTo.toLongOrNull()
                    ?: throw BadRequestException("range_to can't be parsed to Long")
            } ?: todayEpoch
            val bookingRangeFrom: Long = call.request.queryParameters["range_from"]?.let { stringRangeFrom ->
                stringRangeFrom.toLongOrNull()
                    ?: throw BadRequestException("range_from can't be parsed to Long")
            } ?: endOfDayEpoch

            call.respond(bookingFacade.findAll(userId, workspaceId, bookingRangeTo, bookingRangeFrom, returnInstances))
        }
        post(SwaggerDocument.postBookingV1()) {
            val dto = call.receive<BookingRequestDTO>()

            call.response.status(HttpStatusCode.Created)
            val result = bookingFacade.post(dto)
            call.respond(result)
        }
        put("/{id}", SwaggerDocument.putBookingV1()) {
            val id: String = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest)
            val dto = call.receive<BookingRequestDTO>()

            val result = bookingFacade.put(dto, id)
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