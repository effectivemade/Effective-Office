package office.effective

import office.effective.common.exception.ValidationException
import office.effective.common.utils.UuidValidator
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestUuidValidator {
    private val validator = UuidValidator()

    @Test
    fun testValidId() {
        val validId = UUID.randomUUID()

        assertEquals(validId, validator.uuidFromString(validId.toString()))
    }

    @Test
    fun testExceptionWhenInvalidId() {
        val invalidWorkspaceId = "not_a_valid_uuid"

        assertFailsWith<ValidationException> {
            validator.uuidFromString(invalidWorkspaceId)
        }
    }
}