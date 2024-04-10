package office.effective.features.auth.service

import io.ktor.server.application.*
import office.effective.common.exception.InstanceNotFoundException
import office.effective.features.auth.repository.AuthRepository
import org.koin.core.context.GlobalContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

/**
 * [Authorizer] implementation. Check api keys
 * */
class ApiKeyAuthorizer(private val extractor: TokenExtractor = TokenExtractor()) : Authorizer {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val repository: AuthRepository = GlobalContext.get().get()

    /**
     * Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw [InstanceNotFoundException]
     * @param call [ApplicationCall] which contains token to verify
     * @return random String
     * @author Kiselev Danil
     */
    override suspend fun authorize(call: ApplicationCall): Boolean {

        val token = extractor.extractToken(call) ?: run {
            logger.info("Api key authorizer failed")
            return false
        }
        val key = repository.findApiKey(encryptKey("SHA-256", token))

        if (key.isNullOrBlank()) {
            logger.info("Api key authorizer failed")
            logger.trace("Api key authorizer failed with token: {}", token)
            return false
        } else {
            logger.info("Api key authorizer succeed")
            logger.trace("Api key authorizer succeed with token: {}", token)
            return true
        }
    }


    /**
     * Use to encrypt api key before check it in database
     *
     * @param type [String] hash function type
     * @param input [String] input line
     * @return encrypted [String]
     * */
    private fun encryptKey(type: String, input: String): String {
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        return DatatypeConverter.printHexBinary(bytes).uppercase()
    }
}
