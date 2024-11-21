package office.effective.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import office.effective.dto.BookingDTO

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<BookingDTO> { booking ->
            if (booking.beginBooking < 0L || booking.beginBooking >= 2147483647000L)
                ValidationResult.Invalid("beginBooking should be non-negative and less than timestamp max value")
            else if (booking.endBooking < 0L || booking.endBooking >= 2147483647000L)
                ValidationResult.Invalid("endBooking should be non-negative and less than timestamp max value")
            else if (booking.endBooking <= booking.beginBooking)
                ValidationResult.Invalid(
                    "endBooking (${booking.endBooking}) should be greater than beginBooking (${booking.beginBooking})"
                )
            else ValidationResult.Valid
        }
    }
}
