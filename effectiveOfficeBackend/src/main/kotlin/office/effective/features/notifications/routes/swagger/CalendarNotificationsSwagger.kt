/**
 * @suppress
 */
package office.effective.features.notifications.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.IntegrationDTO
import office.effective.dto.UserDTO

/**
 * @suppress
 */
fun SwaggerDocument.receiveNotification(): OpenApiRoute.() -> Unit = {
    description = "Endpoint for receiving Google Calendar notifications"
    tags = listOf("Notifications")
    response {
        HttpStatusCode.OK to {
            description = "Notification was received"
        }
    }
}
