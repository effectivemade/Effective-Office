package office.effective.features.auth.service

import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.slf4j.LoggerFactory

class TokenExtractor {

    /**
     * @param call [ApplicationCall] incoming call
     * @return token from Bearer Auth header or null, if
     * @author Danil Kiselev
     * */
    fun extractToken(call: ApplicationCall): String? {
        val logger = LoggerFactory.getLogger(this::class.java)
        val rendered = call.request.parseAuthorizationHeader()?.render() ?: run {
            logger.info("Cannot find auth token")
            return null
        }
        return rendered.split("Bearer ").last()
    }
}
