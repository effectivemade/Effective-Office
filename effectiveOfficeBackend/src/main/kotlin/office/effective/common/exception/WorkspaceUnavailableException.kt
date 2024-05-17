package office.effective.common.exception

import java.lang.RuntimeException

/**
 * Exception that occurs if workspace is not available for booking
 */
class WorkspaceUnavailableException(message: String): RuntimeException(message)
