package office.effective.common.exception

import java.lang.RuntimeException

/**
 * Represents the exception that occurs during validation of a data
 */
class ValidationException(message: String): RuntimeException(message)
