package office.effective.common.exception

import java.lang.RuntimeException

/**
 * Exception indicating that object id is null, although not null value expected
 */
class MissingIdException(message: String) : RuntimeException(message)
