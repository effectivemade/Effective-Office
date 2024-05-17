package office.effective.common.exception

/**
 * Represents the exception that occurs if current operation is not allowed for some reason.
 * */
class UnavailableOperationException (message: String): RuntimeException(message){
}