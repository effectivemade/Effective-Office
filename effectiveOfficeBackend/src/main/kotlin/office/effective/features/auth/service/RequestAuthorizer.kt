package office.effective.features.auth.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * [Authorizer] implementation. Calls oauth2.googleapis.com to verify token
 * */
class RequestAuthorizer(private val extractor: TokenExtractor = TokenExtractor()) : Authorizer {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Check Google ID Token. Calls oauth2.googleapis.com
     *
     * @param call [ApplicationCall] which contains token to verify
     * @return is token correct
     * */
    override suspend fun authorize(call: ApplicationCall): Boolean {
        val tokenString = extractor.extractToken(call) ?: run {
            logger.info("Request auth failed")
            return false
        }
        val client = HttpClient(CIO) {}
        val response: HttpResponse = client.request("https://oauth2.googleapis.com/tokeninfo") {
            url {
                parameters.append("id_token", tokenString)
            }
        }

        if (response.status != HttpStatusCode.OK) {
            logger.info("Request authorizer failed")
            logger.trace("Request authorizer failed with token: {}", tokenString)
            return false
        } else {
            logger.info("Request authorizer succeed")
            logger.trace("Request authorizer succeed with token: {}", tokenString)
            return true
        }
    }
}
