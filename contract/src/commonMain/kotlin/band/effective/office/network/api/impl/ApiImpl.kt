package band.effective.office.network.api.impl

import band.effective.office.network.api.Api
import band.effective.office.network.api.Collector
import band.effective.office.network.dto.BookingRequestDTO
import band.effective.office.network.dto.BookingResponseDTO
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.KtorEtherClient
import band.effective.office.utils.buildUtils.params
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ApiImpl : Api {
    /**Update collector*/
    val collector = Collector("")

    /**KtorEitherClient for safe request*/
    private val client = KtorEtherClient
    private val baseUrl: String = params().serverUrl
    override suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO> =
        with(getWorkspaces("meeting")) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> data.firstOrNull { it.name == id }.let {
                    if (it == null) {
                        Either.Error(ErrorResponse.getResponse(404))
                    } else {
                        Either.Success(it)
                    }
                }
            }
        }

    override suspend fun getWorkspaces(
        tag: String,
        freeFrom: Long?,
        freeUntil: Long?
    ): Either<ErrorResponse, List<WorkspaceDTO>> =
        client.securityResponse("$baseUrl/api/v1/workspaces") {
            url {
                parameters.append("workspace_tag", tag)
                if (freeFrom != null) {
                    parameters.append("free_from", freeFrom.toString())
                }
                if (freeUntil != null) {
                    parameters.append("free_until", freeUntil.toString())
                }
            }
        }

    override suspend fun getWorkspacesWithBookings(
        tag: String,
        withBookings: Boolean,
        freeFrom: Long?,
        freeUntil: Long?
    ): Either<ErrorResponse, List<WorkspaceDTO>> =
        client.securityResponse("$baseUrl/api/v1/workspaces") {
            url {
                parameters.append("workspace_tag", tag)
                parameters.append("with_bookings", withBookings.toString())
                if (freeFrom != null) {
                    parameters.append("with_bookings_from", freeFrom.toString())
                }
                if (freeUntil != null) {
                    parameters.append("with_bookings_until", freeUntil.toString())
                }
            }
        }


    override suspend fun getZones(): Either<ErrorResponse, List<WorkspaceZoneDTO>> =
        client.securityResponse("$baseUrl/api/v1/workspaces/zones")

    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/api/v1/users") {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getUsers(tag: String): Either<ErrorResponse, List<UserDTO>> =
        client.securityResponse("$baseUrl/api/v1/users") {
            url {
                parameters.append("user_tag", tag)
            }
        }

    override suspend fun updateUser(user: UserDTO): Either<ErrorResponse, UserDTO> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/users",
            method = KtorEtherClient.RestMethod.Put
        ) {
            println("user = $user")
            contentType(ContentType.Application.Json)
            setBody(user)
            url {
                appendPathSegments(user.id)
            }
        }

    override suspend fun getBooking(id: String): Either<ErrorResponse, BookingResponseDTO> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
        ) {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getBookingsByUser(
        userId: String,
        beginDate: Long,
        endDate: Long
    ): Either<ErrorResponse, List<BookingResponseDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
        ) {
            url {
                parameters.append("user_id", userId)
                parameters.append("range_from", beginDate.toString())
                parameters.append("range_to", endDate.toString())
            }
        }

    override suspend fun getBookingsByWorkspaces(
        workspaceId: String,
        from: Long?,
        to: Long?
    ): Either<ErrorResponse, List<BookingResponseDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
        ) {
            url {
                parameters.append("workspace_id", workspaceId)
                if (from != null) {
                    parameters.append("range_from", from.toString())
                }
                if (to != null) {
                    parameters.append("range_to", to.toString())
                }
            }
        }

    override suspend fun createBooking(bookingInfo: BookingRequestDTO): Either<ErrorResponse, BookingResponseDTO> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
            method = KtorEtherClient.RestMethod.Post
        ) {
            contentType(ContentType.Application.Json)
            setBody(bookingInfo)
        }

    override suspend fun updateBooking(
        bookingInfo: BookingRequestDTO,
        bookingId: String
    ): Either<ErrorResponse, BookingResponseDTO> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
            method = KtorEtherClient.RestMethod.Put
        ) {
            url {
                appendPathSegments(bookingId)
            }
            contentType(ContentType.Application.Json)
            val body = Json.encodeToString(bookingInfo)
            println(bookingInfo)
            setBody(bookingInfo)
        }

    override suspend fun deleteBooking(bookingId: String): Either<ErrorResponse, SuccessResponse> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
            method = KtorEtherClient.RestMethod.Delete
        ) {
            url {
                appendPathSegments(bookingId)
            }
        }

    override fun subscribeOnWorkspaceUpdates(
        id: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorResponse, WorkspaceDTO>> =
        collector.flow(scope).filter { it == "workspace" }.map {
            Either.Success(
                WorkspaceDTO(
                    id = "", name = "",
                    utilities = listOf(), zone = null, tag = ""
                )
            )
        }

    override fun subscribeOnOrganizersList(scope: CoroutineScope): Flow<Either<ErrorResponse, List<UserDTO>>> =
        collector.flow(scope).filter { it == "organizer" }.map { Either.Success(listOf()) }


    override suspend fun getUserByEmail(email: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/api/v1/users") {
            url {
                parameters.append(name = "email", value = email)
            }
        }

    override suspend fun getBookings(
        rangeFrom: Long?,
        rangeTo: Long?
    ): Either<ErrorResponse, List<BookingResponseDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/api/v1/bookings",
        ) {
            url {
                if (rangeFrom != null) {
                    parameters.append("range_from", rangeFrom.toString())
                }
                if (rangeTo != null) {
                    parameters.append("range_to", rangeTo.toString())
                }
            }
        }

    override fun subscribeOnBookingsList(
        workspaceId: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorResponse, List<BookingResponseDTO>>> =
        collector.flow(scope).filter { it == "booking" }
            .map { Either.Success(listOf()) }
}