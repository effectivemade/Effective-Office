package office.effective.features.auth.service

import io.ktor.server.application.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Encapsulate pipeline of authorizers
 * */
class AuthorizationPipeline : Authorizer {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val authorizers: MutableCollection<Authorizer> = mutableListOf()

    override suspend fun authorize(call: ApplicationCall): Boolean {

        if (authorizers.isEmpty()) {
            logger.warn("Authorizers collection is empty")
            return false
        }

        authorizers.forEach {
            if (it.authorize(call)) {
                return@authorize true
            }
        }
        return false
    }

    /**
     * Allow you to add authorizer to pipeline
     * @param authorizer [Authorizer]
     * @return [AuthorizationPipeline] - instance of pipeline
     * */
    fun addAuthorizer(authorizer: Authorizer): AuthorizationPipeline {
        authorizers.add(authorizer)
        return this
    }

}
