package office.effective.common.exception

import java.lang.RuntimeException

/**
 * Exception that occurs if workspace can't be booked
 */
class WorkspaceUnavailableException(message: String): RuntimeException(message)
