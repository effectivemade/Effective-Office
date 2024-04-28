package office.effective.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import office.effective.dto.BookingDTO
import office.effective.dto.BookingRequestDTO

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<BookingDTO> { booking ->
            if (booking.beginBooking < 0L)
                ValidationResult.Invalid("beginBooking must be non-negative")
            else if (booking.endBooking < 0L)
                ValidationResult.Invalid("endBooking must be non-negative")
            else if (booking.endBooking <= booking.beginBooking)
                ValidationResult.Invalid(
                    "endBooking (${booking.endBooking}) must be greater than beginBooking (${booking.beginBooking})"
                )
            else ValidationResult.Valid
        }
        validate<BookingRequestDTO> { booking ->
            if (booking.beginBooking < 0L)
                ValidationResult.Invalid("beginBooking must be non-negative")
            else if (booking.endBooking < 0L)
                ValidationResult.Invalid("endBooking must be non-negative")
            else if (booking.endBooking <= booking.beginBooking)
                ValidationResult.Invalid(
                    "endBooking (${booking.endBooking}) must be greater than beginBooking (${booking.beginBooking})"
                )
            else ValidationResult.Valid
        }
    }
}
