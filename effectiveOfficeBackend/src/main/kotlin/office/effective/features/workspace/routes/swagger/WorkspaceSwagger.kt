/**
 * @suppress
 */
package office.effective.features.workspace.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.UtilityDTO
import office.effective.dto.WorkspaceDTO
import office.effective.dto.WorkspaceZoneDTO

/**
 * @suppress
 */
fun SwaggerDocument.returnWorkspaceById(): OpenApiRoute.() -> Unit = {
    description = "Return workspace by id"
    tags = listOf("workspaces")
    request {
        pathParameter<String>("id") {
            description = "Workspace id"
            example = "2561471e-2bc6-11ee-be56-0242ac120002"
            required = true
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns workspace found by id"
            body<WorkspaceDTO> {
                example(
                    "Workspaces", WorkspaceDTO(
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
                    )
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
        HttpStatusCode.NotFound to {
            description = "Workspace with this id was not found"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.returnWorkspaceByTag(): OpenApiRoute.() -> Unit = {
    description = "Return all workspaces by tag"
    tags = listOf("workspaces")
    request {
        queryParameter<WorkspaceTag>("workspace_tag") {
            description = "Workspace tag"
            example = "meeting"
            required = true
            allowEmptyValue = false
        }
        queryParameter<Long>("free_from") {
            description = "Timestamp from which the workspace should be free"
            example = 1691591501000L
            required = false
            allowEmptyValue = false
        }
        queryParameter<Long>("free_until") {
            description = "Timestamp before which the workspace should be free."
            example = 1691591578000L
            required = false
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns all workspaces found by tag"
            body<List<WorkspaceDTO>> {
                example(
                    "Workspace", listOf(
                        WorkspaceDTO(
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
                        ), WorkspaceDTO(
                            id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Moon", tag = "meeting",
                            utilities = listOf(
                                UtilityDTO(
                                    id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                    name = "Sockets",
                                    iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                    count = 5
                                ),
                            )
                        )
                    )
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Tag shouldn't be null, free_from " +
                    "and free_until should be numbers from 0 to 2147483647000 (max timestamp)"
        }
        HttpStatusCode.NotFound to {
            description = "Provided tag doesn't exist"
        }
    }
}

/**
 * @suppress
 */
fun SwaggerDocument.returnAllZones(): OpenApiRoute.() -> Unit = {
    description = "Returns all workspace zones"
    tags = listOf("workspaces")
    response {
        HttpStatusCode.OK to {
            description = "Returns all workspaces found by tag"
            body<List<WorkspaceZoneDTO>> {
                example(
                    "Zones", listOf(
                        zoneExample1, zoneExample2
                    )
                ) {}
            }
        }
    }
}

/**
 * @suppress
 */
private val zoneExample1 = WorkspaceZoneDTO("3ca26fe0-f837-4939-b586-dd4195d2a504","Cassiopeia")
/**
 * @suppress
 */
private val zoneExample2 = WorkspaceZoneDTO("6cb3c60d-3c29-4a45-80e6-fac14fb0569b","Sirius")

/**
 * @suppress
 */
enum class WorkspaceTag(val tagName: String) {
    meeting("meeting"), regular("regular")
}
