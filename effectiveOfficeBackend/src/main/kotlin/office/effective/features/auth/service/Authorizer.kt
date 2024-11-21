package office.effective.features.auth.service

import io.ktor.server.application.*

/**
 * Interface for authorization chain of responsibility element
 * */
interface Authorizer {
    /**
     * @param call [ApplicationCall] incoming call to authorize
     * @author Kiselev Danil
     * */
    suspend fun authorize(call: ApplicationCall): Boolean
}
