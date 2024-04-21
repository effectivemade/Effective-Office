package office.effective.features.workspace.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.constants.BookingConstants
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.workspace.facade.WorkspaceFacadeV1
import office.effective.features.workspace.routes.swagger.*
import org.koin.core.context.GlobalContext
import java.time.LocalDate
import java.time.ZoneId

fun Route.workspaceRoutingV1() {
    route("/api/v1/workspaces") {
        val facade: WorkspaceFacadeV1 = GlobalContext.get().get()
        val oneDayMillis: Long = 1000*60*60*24;

        get("/{id}", SwaggerDocument.returnWorkspaceByIdV1()) {
            val id: String = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(facade.findById(id))
        }
        get(SwaggerDocument.returnWorkspaceByTagV1()) {
            val tag: String = call.request.queryParameters["workspace_tag"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)
            val freeFromString: String? = call.request.queryParameters["free_from"]
            val freeUntilString: String? = call.request.queryParameters["free_until"]
            val withBookingsString: String? = call.request.queryParameters["with_bookings"]
            val withBookingsFromString: String? = call.request.queryParameters["with_bookings_from"]
            val withBookingsUntilString: String? = call.request.queryParameters["with_bookings_until"]

            if (freeFromString == null && freeUntilString == null) {
                var withBookingsFrom: Long? = null
                var withBookingsUntil: Long? = null
                val withBookings = withBookingsString?.let { paramString ->
                    paramString.toBooleanStrictOrNull()
                        ?: throw BadRequestException("with_bookings can't be parsed to boolean")
                } ?: false
                if (withBookings || withBookingsFromString != null || withBookingsUntilString != null) {
                    val todayEpoch = LocalDate.now()
                        .atStartOfDay(ZoneId.of(BookingConstants.DEFAULT_TIMEZONE_ID))
                        .toInstant()
                        .toEpochMilli() + BookingConstants.DEFAULT_TIMEZONE_OFFSET_MILLIS
                    val endOfDayEpoch = todayEpoch + oneDayMillis
                    withBookingsFrom = withBookingsFromString?.let { paramString ->
                        paramString.toLongOrNull()
                            ?: throw BadRequestException("with_bookings_from can't be parsed to Long")
                    } ?: todayEpoch
                    withBookingsUntil = withBookingsUntilString?.let { paramString ->
                        paramString.toLongOrNull()
                            ?: throw BadRequestException("with_bookings_until can't be parsed to Long")
                    } ?: endOfDayEpoch
                }
                call.respond(facade.findAllByTag(tag, withBookingsFrom, withBookingsUntil))
            } else {
                val freeFrom: Long = freeFromString?.toLongOrNull()
                    ?: throw BadRequestException("free_from not specified or invalid")
                val freeUntil: Long = freeUntilString?.toLongOrNull()
                    ?: throw BadRequestException("free_until not specified or invalid")
                call.respond(facade.findAllFreeByPeriod(tag, freeFrom, freeUntil))
            }
        }
        get("/zones", SwaggerDocument.returnAllZonesV1()) {
            call.respond(facade.findAllZones())
        }
    }
}
