/**
 * @suppress
 */
package office.effective.features.booking.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.constants.BookingConstants
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.BookingDTO
import office.effective.dto.IntegrationDTO
import office.effective.dto.UserDTO
import office.effective.dto.UtilityDTO
import office.effective.dto.WorkspaceDTO
import java.time.Instant

/**
 * @suppress
 */
fun SwaggerDocument.returnBookingByIdV1(): OpenApiRoute.() -> Unit = {
    description = "Returns booking found by id"
    tags = listOf("bookings")
    request {
        pathParameter<String>("id") {
            description = "Booking id"
            example = "c48c2a3d-bbfd-4801-b121-973ae3cf4cd9"
            required = true
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns booking found by id"
            body<BookingDTO> {
                example(
                    "Bookings", bookingExample1
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
        HttpStatusCode.NotFound to {
            description = "Booking with this id was not found"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.returnBookingsV1(): OpenApiRoute.() -> Unit = {
    description = "Return all bookings. Bookings can be filtered by booking owner id, workspace id and time range. " +
            "Returns only non-recurring bookings (recurring bookings are expanded into non-recurring ones). " +
            "Can return no more than 2500 bookings."
    tags = listOf("bookings")
    request {
        queryParameter<String>("user_id") {
            description = "Booking owner id"
            example = "2c77feee-2bc1-11ee-be56-0242ac120002"
            required = false
            allowEmptyValue = false
        }
        queryParameter<String>("workspace_id") {
            description = "Booked workspace id"
            example = "50d89406-2bc6-11ee-be56-0242ac120002"
            required = false
            allowEmptyValue = false
        }
        queryParameter<Long>("range_from") {
            description = "Lower bound (exclusive) for a endBooking to filter by. Should be lover than range_to. " +
                    "Default value: ${BookingConstants.MIN_SEARCH_START_TIME} " +
                    "(${Instant.ofEpochMilli(BookingConstants.MIN_SEARCH_START_TIME)}). " +
                    "Old Google calendar events may not appear correctly in the system and cause unexpected exceptions"
            example = 1692927200000
            required = false
            allowEmptyValue = false

        }
        queryParameter<Long>("range_to") {
            description = "Upper bound (exclusive) for a beginBooking to filter by. Optional. " +
                    "Should be greater than range_from."
            example = 1697027200000
            required = false
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns all bookings found by user id"
            body<List<BookingDTO>> {
                example(
                    "Workspace", listOf(
                        bookingExample1, bookingExample2
                    )
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "range_to isn't greater then range_to, or one of them can't be parsed to Long"
        }
        HttpStatusCode.NotFound to {
            description = "User or workspace with the given id doesn't exist"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.postBookingV1(): OpenApiRoute.() -> Unit = {
    description = "Saves a given booking"
    tags = listOf("bookings")
    request {
        body<BookingDTO> {
            example(
                "Bookings", BookingDTO(
                    owner = UserDTO(
                        id = "2c77feee-2bc1-11ee-be56-0242ac120002",
                        fullName = "Max",
                        active = true,
                        role = "Fullstack developer",
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "89087659880"
                            )
                        ),
                        email = "cool.fullstack.developer@effective.band",
                        tag = "employee"
                    ),
                    participants = listOf(
                        UserDTO(
                            id = "2c77feee-2bc1-11ee-be56-0242ac120002",
                            fullName = "Ivan Ivanov",
                            active = true,
                            role = "Android developer",
                            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                            integrations = listOf(
                                IntegrationDTO(
                                    "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                    "phoneNumber",
                                    "89236379887"
                                )
                            ),
                            email = "cool.backend.developer@effective.band",
                            tag = "employee"
                        )
                    ),
                    workspace = WorkspaceDTO(
                        id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Sun", tag = "meeting",
                        utilities = listOf(
                            UtilityDTO(
                                id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                name = "Sockets",
                                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                count = 8
                            ), UtilityDTO(
                                id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                                name = "Projectors",
                                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                count = 1
                            )
                        )
                    ),
                    id = null,
                    beginBooking = 1691299526000,
                    endBooking = 1691310326000,
                    recurrence = null
                )
            )
        }
    }
    response {
        HttpStatusCode.Created to {
            description = "Returns saved booking"
            body<BookingDTO> {
                example(
                    "Bookings", bookingExample2
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Invalid request body or workspace can't be booked in a given period"
        }
        HttpStatusCode.NotFound to {
            description = "User or workspace with the given id doesn't exist"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.putBookingV1(): OpenApiRoute.() -> Unit = {
    description = "Updates a given booking"
    tags = listOf("bookings")
    request {
        body<BookingDTO> {
            example(
                "Bookings", bookingExample1
            )
        }
        pathParameter<String>("id") {
            description = "Booking id"
            example = "p0v9udrhk66cailnigi0qkrji4"
            required = true
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns saved booking"
            body<BookingDTO> {
                example(
                    "Bookings", bookingExample1
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Invalid request body or workspace can't be booked in a given period"
        }
        HttpStatusCode.NotFound to {
            description = "Booking, user or workspace with the given id doesn't exist"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.deleteBookingByIdV1(): OpenApiRoute.() -> Unit = {
    description =
        "Deletes a booking with the given id. If the booking is not found in the database it is silently ignored"
    tags = listOf("bookings")
    request {
        pathParameter<String>("id") {
            description = "Booking id"
            example = "c48c2a3d-bbfd-4801-b121-973ae3cf4cd9"
            required = true
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.NoContent to {
            description = "Booking was successfully deleted"
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
    }
}

/**
 * @suppress
 */
private val bookingExample1 = BookingDTO(
    owner = UserDTO(
        id = "2c77feee-2bc1-11ee-be56-0242ac120002",
        fullName = "Ivan Ivanov",
        active = true,
        role = "Backend developer",
        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
        integrations = listOf(
            IntegrationDTO(
                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                "phoneNumber",
                "89236379887"
            )
        ),
        email = "cool.backend.developer@effective.band",
        tag = "employee"
    ),
    participants = listOf(
        UserDTO(
            id = "2c77feee-2bc1-11ee-be56-0242ac120002",
            fullName = "Ivan Ivanov",
            active = true,
            role = "Backend developer",
            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
            integrations = listOf(
                IntegrationDTO(
                    "c717cf6e-28b3-4148-a469-032991e5d9e9",
                    "phoneNumber",
                    "89236379887"
                )
            ),
            email = "cool.backend.developer@effective.band",
            tag = "employee"
        ),
        UserDTO(
            id = "207b9634-2bc4-11ee-be56-0242ac120002",
            fullName = "Grzegorz Brzęczyszczykiewicz",
            active = true,
            role = "Guest",
            avatarUrl = "https://img.freepik.com/free-photo/capybara-in-the-nature-habitat-of-northern-pantanal_475641-1029.jpg",
            integrations = listOf(
                IntegrationDTO(
                    "c717cf6e-28b3-4148-a469-032991e5d9e9",
                    "phoneNumber",
                    "89086379880"
                )
            ),
            email = "email@yahoo.com",
            tag = "employee"
        )
    ),
    workspace = WorkspaceDTO(
        id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Sun", tag = "meeting",
        utilities = listOf(
            UtilityDTO(
                id = "50d89406-2bc6-11ee-be56-0242ac120002",
                name = "Sockets",
                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                count = 8
            ), UtilityDTO(
                id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                name = "Projectors",
                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                count = 1
            )
        )
    ),
    id = "c48c2a3d-bbfd-4801-b121-973ae3cf4cd9",
    beginBooking = 1691299526000,
    endBooking = 1691310326000,
    recurrence = null
)

/**
 * @suppress
 */
private val bookingExample2 = BookingDTO(
    owner = UserDTO(
        id = "2c77feee-2bc1-11ee-be56-0242ac120002",
        fullName = "Ivan Ivanov",
        active = true,
        role = "Backend developer",
        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
        integrations = listOf(
            IntegrationDTO(
                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                "phoneNumber",
                "89236379887"
            )
        ),
        email = "cool.backend.developer@effective.band",
        tag = "employee"
    ),
    participants = listOf(
        UserDTO(
            id = "2c77feee-2bc1-11ee-be56-0242ac120002",
            fullName = "Ivan Ivanov",
            active = true,
            role = "Backend developer",
            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
            integrations = listOf(
                IntegrationDTO(
                    "c717cf6e-28b3-4148-a469-032991e5d9e9",
                    "phoneNumber",
                    "89236379887"
                )
            ),
            email = "cool.backend.developer@effective.band",
            tag = "employee"
        )
    ),
    workspace = WorkspaceDTO(
        id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Sun", tag = "meeting",
        utilities = listOf(
            UtilityDTO(
                id = "50d89406-2bc6-11ee-be56-0242ac120002",
                name = "Sockets",
                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                count = 8
            ), UtilityDTO(
                id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                name = "Projectors",
                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                count = 1
            )
        )
    ),
    id = "c48c2a3d-bbfd-4801-b121-973ae3cf4cd9",
    beginBooking = 1691299526000,
    endBooking = 1691310326000,
    recurrence = null
)
