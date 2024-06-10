package office.effective.features.auth.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.ktor.server.application.*
import office.effective.config
import office.effective.features.user.service.UserService
import office.effective.serviceapi.IUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Implementation of [Authorizer]. Checks GoogleIdTokens
 * */
class TokenAuthorizer(private val extractor: TokenExtractor = TokenExtractor(), private val userService: IUserService) : Authorizer {

    private val verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory()).build()

    private val acceptableMailDomain: String =
        config.propertyOrNull("auth.user.emailDomain ")?.getString() ?: "effective.band"
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Check Google ID Token using google library
     *
     * @param call [String] which contains token to verify
     * @throws Exception("Token wasn't verified by Google") if token does not contain payload
     * @return is token correct
     * */
    override suspend fun authorize(call: ApplicationCall): Boolean {
        var userMail: String? = null
        val tokenString = extractor.extractToken(call) ?: run {
            logger.info("Token auth failed")
            return false
        }
        val token: GoogleIdToken?
        try {
            token = verifier.verify(tokenString)
        } catch (ex: Exception) {
            return failResponse(tokenString)
        }

        val payload = token?.payload ?: run { return@authorize failResponse(tokenString) }

        val emailVerified: Boolean = payload.emailVerified
        val hostedDomain = payload.hostedDomain ?: extractDomain(payload.email)
        if ((acceptableMailDomain == hostedDomain) && emailVerified) {
            userMail = payload.email
        }

        if (userMail.isNullOrBlank()) {
            logger.info("Token auth failed")
            logger.trace("Token auth with token: {}", tokenString)
            return false
        } else {
            val user = userService.getUserByEmail(userMail)
            if(user == null) {
                logger.info("Token auth failed")
                logger.trace("Token auth with token: {}", tokenString)
                return false
            }
            logger.info("Token auth succeed")
            return true
        }

    }

    /**
     * @param email - [String] from Bearer auth header, which must contains email
     * @throws Exception("Email is empty") if input line is null
     * @return [String], which contains email address
     * */
    private fun extractDomain(email: String?): String {
        email ?: throw Exception("Email is empty")
        return email.split('@').last()
    }

    private fun failResponse(tokenString: String): Boolean {
        logger.info("Token auth failed")
        logger.trace("Token auth failed with token: {}", tokenString)
        return false
    }
}
