package office.effective.common.utils

import office.effective.common.exception.ValidationException
import java.lang.IllegalArgumentException
import java.util.UUID

/**
 * Class for UUID validation and conversion
 */
class UuidValidator {
    /**
     * Converts string to UUID
     *
     * @throws ValidationException if provided string is not valid UUID
     */
    fun uuidFromString(stringId: String): UUID {
        try {
            return UUID.fromString(stringId)
        } catch (ex: IllegalArgumentException) {
            throw ValidationException("Provided id is not UUID: " + ex.message)
        }
    }
}