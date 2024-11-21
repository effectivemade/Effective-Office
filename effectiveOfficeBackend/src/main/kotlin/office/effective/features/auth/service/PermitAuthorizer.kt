package office.effective.features.auth.service

import io.ktor.server.application.*
import io.ktor.server.request.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Authorizer to permit routes. Permit all child routes, if parent permitted.
 * Example: "/workspaces" permission allow access to "/workspaces/zones" routes.
 * @author Danil Kiselev
 * */
class PermitAuthorizer(private val permittedPaths: Iterable<String>) : Authorizer {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun authorize(call: ApplicationCall): Boolean {
        val currentPathAsArray = call.request.path().split('/').filter { it.isNotBlank() }
        for (permitted in permittedPaths) {
            val permittedPathAsArray = permitted.split('/').filter { it.isNotBlank() }
            if (permittedPathAsArray.size <= currentPathAsArray.size) {
                for (i in permittedPathAsArray.indices) {
                    if (permittedPathAsArray[i] == currentPathAsArray[i]) {
                        return true
                    }
                }
            }
        }
        return false
    }


}
