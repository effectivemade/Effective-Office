package band.effective.office.elevator.plugins

import band.effective.office.common.utils.DateUtils
import band.effective.office.common.utils.HashUtil
import band.effective.office.common.utils.PropertiesUtil
import band.effective.office.common.utils.toGMTDate
import band.effective.office.common.utils.toVerifiableDate
import band.effective.office.elevator.utils.ElevatorController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.date.*


fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond("Raspberry Client API")
        }

        post("/office-elevator") {
            val time = call.request.queryParameters["time"]?.toGMTDate()
            val token = call.request.queryParameters["token"]

            when {
                call.request.queryParameters["command"] != "go" -> call.respond(HttpStatusCode.NotFound)
                    .apply {
                        println("""[NotFound]: queryParameters["command"] != "go" """)
                    }

                !DateUtils.isCorrectTime(time) -> {
                    call.respond(
                        HttpStatusCode.Forbidden, "Incorrect request time",
                    ).apply {
                        println("[Forbidden]: Incorrect request time")
                    }
                }

                token != HashUtil.sha256(
                    value = GMTDate().toVerifiableDate(),
                    password = PropertiesUtil.read("OFFICE_ELEVATOR_EXCHANGE_PASSWORD")
                ) -> {
                    call.respond(
                        HttpStatusCode.Forbidden,
                        "Verification token is incorrect"
                    ).apply {
                        println("[Forbidden]: Verification token is incorrect")
                    }
                }

                else -> {
                    ElevatorController.call()
                    call.respond(HttpStatusCode.OK, "Elevator called")
                }
            }
        }
    }
}