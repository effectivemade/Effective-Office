package band.effective.office.elevator.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PrettifyPhoneNumberTest {

    @Test
    fun worksWellForPhoneNumbersWithoutPlus() {
        val prettifiedPhoneNumber = prettifyPhoneNumber("79136188085")
        assertEquals("+7 913 618 80-85", prettifiedPhoneNumber)
    }

    @Test
    fun worksWellForPhoneNumberWithPlus() {
        val prettifiedPhoneNumber = prettifyPhoneNumber("+79136188085")
        assertEquals("+7 913 618 80-85", prettifiedPhoneNumber)
    }

    @Test
    fun returnsNullForPhoneNumberWithInvalidCharacter() {
        val prettifiedPhoneNumber = prettifyPhoneNumber("sdsdsdsdsd")
        assertNull(prettifiedPhoneNumber)
    }

    @Test
    fun returnsNullForInvalidLength() {
        val prettifiedPhoneNumber = prettifyPhoneNumber("79138085")
        assertNull(prettifiedPhoneNumber)
    }

    @Test
    fun worksWellForPartiallyFormatedPhoneNumber() {
        val prettifiedPhoneNumber = prettifyPhoneNumber("+7 9136188085")
        assertEquals("+7 913 618 80-85", prettifiedPhoneNumber)
    }

    @Test
    fun worksWellForFormatedPhoneNumber() {
        val prettifiedPhoneNumber = prettifyPhoneNumber("+7 913 618 80-85")
        assertEquals("+7 913 618 80-85", prettifiedPhoneNumber)
    }
}
