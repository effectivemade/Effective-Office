/**
 * @suppress
 */
package office.effective.features.user.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.IntegrationDTO
import office.effective.dto.UserDTO

/**
 * @suppress
 */
fun SwaggerDocument.returnUsersV1(): OpenApiRoute.() -> Unit = {
    description = "Return all users, all users by tag or one user by email"
    tags = listOf("users")
    request{
        queryParameter<String>("user_tag"){
            description = "Name of the tag. Mutually exclusive with email"
            example = "employee"
            required = false
            allowEmptyValue = false
        }
        queryParameter<String>("email") {
            description = "User's email. Mutually exclusive with tag"
            example = "cool.backend.developer@effective.band"
            required = false
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Return users list or single user"
            body<List<UserDTO>> {
                example(
                    "Users",
                    listOf(
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
                                ), IntegrationDTO(
                                    "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                    "telegram",
                                    "@cooldeveloper"
                                )
                            ),
                            email = "cool.backend.developer@effective.band",
                            tag = "employee"
                        ),
                        UserDTO(
                            id = "207b9634-2bc4-11ee-be56-0242ac120002",
                            fullName = "Danil Egorov",
                            active = true,
                            role = "Android developer",
                            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                            integrations = listOf(IntegrationDTO(
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "89038760982")
                            ),
                            email = "cool.android.developer@effective.band",
                            tag = "employee"
                        )
                    )
                ) {
                    summary = "Return users list by tag"
                }
                example(
                    "User",
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
                            ), IntegrationDTO(
                                "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                "telegram",
                                "@cooldeveloper"
                            ), IntegrationDTO(
                                "e321f85c-3f85-4c23-8e59-05005a82ffa8",
                                "Foobr",
                                "CoolDev@foobr.ru"
                            )
                        ),
                        email = "cool.backend.developer@effective.band",
                        tag = "employee"
                    )
                ) {
                    summary = "Return user by email"
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Email and tag are mutually exclusive parameters"
        }
        HttpStatusCode.NotFound to {
            description = "Provided tag doesn't exist or the user was not found when searching by email"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.returnUserByIdV1(): OpenApiRoute.() -> Unit = {
    description = "Return user by id"
    tags = listOf("users")
    request {
        pathParameter<String>("user_id") {
            description = "User id"
            example = "87e66ee0-2550-4188-8d79-75560125836a"
            required = true
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns user found by id"
            body<UserDTO> {
                example(
                    "User",
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
                            ), IntegrationDTO(
                                "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                "telegram",
                                "@cooldeveloper"
                            )
                        ),
                        email = "cool.android.developer@effective.band",
                        tag = "employee"
                    )
                ) {
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
        HttpStatusCode.NotFound to {
            description = "User with this id was not found"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.updateUserV1(): OpenApiRoute.() -> Unit = {
    description = "Changes user by id"
    tags = listOf("users")
    request {
        pathParameter<String>("user_id") {
            description = "User id"
            example = "87e66ee0-2550-4188-8d79-75560125836a"
            required = true
            allowEmptyValue = false
        }
        body<UserDTO> {
            required = true
            example(
                "User",
                UserDTO(
                    id = "87e66ee0-2550-4188-8d79-75560125836a",
                    fullName = "I'm a geno-modified GenaCrocodile",
                    active = true,
                    role = "mimocrocodile",
                    avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                    integrations = listOf(
                        IntegrationDTO(
                            "c717cf6e-28b3-4148-a469-032991e5d9e9",
                            "phoneNumber",
                            "88009000000"
                        ), IntegrationDTO(
                            "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                            "telegram",
                            "@mimocrocodile"
                        )
                    ),
                    email = "mimocrocodile@mimo.crocodile",
                    tag = "guest"
                )
            )
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Return users dto"
            body<UserDTO> {
                example(
                    "User",
                    UserDTO(
                        id = "87e66ee0-2550-4188-8d79-75560125836a",
                        fullName = "I'm a geno-modified GenaCrocodile",
                        active = true,
                        role = "mimocrocodile",
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "88009000000"
                            ), IntegrationDTO(
                                "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                "telegram",
                                "@mimocrocodile"
                            )
                        ),
                        email = "mimocrocodile@mimo.crocodile",
                        tag = "guest"
                    )
                ) {
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
        HttpStatusCode.NotFound to {
            description = "User with this id not found"
        }
    }
}
