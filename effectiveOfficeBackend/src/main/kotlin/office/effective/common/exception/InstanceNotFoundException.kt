package office.effective.common.exception

import java.util.*
import kotlin.reflect.KClass

/**
 * Exception indicating that the requested resource was not found in the database.
 */
class InstanceNotFoundException(targetClass: KClass<*>, message: String, id: UUID? = null): RuntimeException(message)