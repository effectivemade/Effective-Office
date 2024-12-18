package com.example

import com.example.models.Workplace
import com.example.models.WorkplacesResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

private val json = Json {
    prettyPrint = true
    isLenient = true
}

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/save-map") {
            val bytes = call.receive<ByteArray>()
            File("files/office_source_map.svg").copyTo(
                File("files/office_source_map_backup.svg"),
                true
            )
            File("files/office_source_map.svg").writeBytes(bytes)
            call.respond(HttpStatusCode.OK)
        }

        staticFiles("/resources", File("files"))

        get("/workspaces") {
            val database = File("files/database.json").readBytes().decodeToString()
            call.respond(
                status = HttpStatusCode.OK,
                message = json.decodeFromString<WorkplacesResponse>(database),
            )
        }

        post("/workspaces/new") {
            val newWorkplace = call.receive<Workplace>()
            val database = File("files/database.json").readBytes().decodeToString()
            val workplaceResponse = json.decodeFromString<WorkplacesResponse>(database)
            val workplaces = workplaceResponse.list.toMutableList()
            workplaces.add(newWorkplace)
            val byteArray =
                json.encodeToString<WorkplacesResponse>(workplaceResponse.copy(list = workplaces)).encodeToByteArray()
            File("files/database.json").writeBytes(byteArray)
            call.respond(HttpStatusCode.OK)
        }
    }
}
