package band.effective.office.network.api.impl

import band.effective.office.network.api.Api
import band.effective.office.network.api.Collector
import band.effective.office.network.dto.avatar.AvatarDTO
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO
import band.effective.office.network.dto.avatar.AvatarRequestBody
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.KtorEtherClient
import band.effective.office.utils.KtorEtherClient.avatarHttpEngine
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
import kotlinx.serialization.json.decodeFromJsonElement

class ApiImpl : Api {
    /**Update collector*/
    val collector = Collector("")

    /**KtorEitherClient for safe request*/
    private val client = KtorEtherClient
    private val baseUrl: String = params().serverUrl
    private val baseAvatarUrl: String = "https://avatarapi.com/v2/api.aspx" //TODO: Remove hardcode

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
        client.securityResponse("$baseUrl/workspaces") {
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

    override suspend fun getZones(): Either<ErrorResponse, List<WorkspaceZoneDTO>> =
        client.securityResponse("$baseUrl/workspaces/zones")

    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/users") {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getUsers(tag: String): Either<ErrorResponse, List<UserDTO>> =
        client.securityResponse("$baseUrl/users") {
            url {
                parameters.append("user_tag", tag)
            }
        }

    override suspend fun updateUser(user: UserDTO): Either<ErrorResponse, UserDTO> =
        client.securityResponse(
            urlString = "$baseUrl/users",
            method = KtorEtherClient.RestMethod.Put
        ) {
            println("user = $user")
            contentType(ContentType.Application.Json)
            setBody(user)
            url {
                appendPathSegments(user.id)
            }
        }

    override suspend fun getBooking(id: String): Either<ErrorResponse, BookingDTO> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
        ) {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getBookingsByUser(
        userId: String,
        beginDate: Long,
        endDate: Long
    ): Either<ErrorResponse, List<BookingDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
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
    ): Either<ErrorResponse, List<BookingDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
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

    override suspend fun createBooking(bookingInfo: BookingDTO): Either<ErrorResponse, BookingDTO> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
            method = KtorEtherClient.RestMethod.Post
        ) {
            contentType(ContentType.Application.Json)
            setBody(bookingInfo)
        }

    override suspend fun updateBooking(
        bookingInfo: BookingDTO
    ): Either<ErrorResponse, BookingDTO> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
            method = KtorEtherClient.RestMethod.Put
        ) {
            contentType(ContentType.Application.Json)
            val body = Json.encodeToString(bookingInfo)
            println(bookingInfo)
            setBody(bookingInfo)
        }

    override suspend fun deleteBooking(bookingId: String): Either<ErrorResponse, SuccessResponse> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
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
        client.securityResponse("$baseUrl/users") {
            url {
                parameters.append(name = "email", value = email)
            }
        }

    override suspend fun getBookings(
        rangeFrom: Long?,
        rangeTo: Long?
    ): Either<ErrorResponse, List<BookingDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
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
    ): Flow<Either<ErrorResponse, List<BookingDTO>>> =
        collector.flow(scope).filter { it == "booking" }
            .map { Either.Success(listOf()) }

    override suspend fun getUserAvatar(
        avatarRequestBody: AvatarRequestBody
    ): Either<ErrorResponse, AvatarDTO> = client.securityResponse(
        urlString = baseAvatarUrl,
        method = KtorEtherClient.RestMethod.Post,
        client = avatarHttpEngine
    ) {
        contentType(ContentType.Application.Json)
        setBody(avatarRequestBody)
    }

    override suspend fun subscribeOnAvatar(scope: CoroutineScope): Flow<Either<ErrorResponse, AvatarDTO>> =
        collector.flow(scope).filter { it == "avatar" }.map { Either.Success(data = AvatarDTO()) }
}