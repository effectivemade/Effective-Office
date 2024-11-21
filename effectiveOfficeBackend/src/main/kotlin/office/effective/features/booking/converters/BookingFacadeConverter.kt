package office.effective.features.booking.converters

import model.RecurrenceDTO
import office.effective.dto.BookingDTO
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.model.*
import org.slf4j.LoggerFactory
import java.time.Instant

/**
 * Converts between [BookingDTO] and [Booking]
 *
 * Uses [UserDTOModelConverter] and [WorkspaceFacadeConverter] to convert contained users and workspaces
 */
class BookingFacadeConverter(private val userConverter: UserDTOModelConverter,
                             private val workspaceConverter: WorkspaceFacadeConverter) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Converts [Booking] to [BookingDTO]
     *
     * @param booking [Booking] to be converted
     * @return The resulting [BookingDTO] object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun modelToDto(booking: Booking): BookingDTO {
        logger.trace("Converting booking model to dto")
        var recurrenceDTO : RecurrenceDTO? = null
        if(booking.recurrence != null) {
            recurrenceDTO = RecurrenceConverter.modelToDto(booking.recurrence!!)
        }
        return BookingDTO(
            owner = userConverter.modelToDTO(booking.owner),
            participants = booking.participants.map { userConverter.modelToDTO(it) },
            workspace = workspaceConverter.modelToDto(booking.workspace),
            id = booking.id.toString(),
            beginBooking = booking.beginBooking.toEpochMilli(),
            endBooking = booking.endBooking.toEpochMilli(),
            recurrence = recurrenceDTO
        )
    }

    /**
     * Converts [BookingDTO] to [Booking]
     *
     * @param bookingDTO [BookingDTO] to be converted
     * @return The resulting [Booking] object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun dtoToModel(bookingDTO: BookingDTO): Booking {
        logger.trace("Converting booking dto to model")
        var recurrenceModel : RecurrenceModel? = null
        if(bookingDTO.recurrence != null) {
            recurrenceModel = RecurrenceConverter.dtoToModel(bookingDTO.recurrence)
        }
        return Booking(
            owner = userConverter.dTOToModel(bookingDTO.owner),
            participants = bookingDTO.participants.map { userConverter.dTOToModel(it) },
            workspace = workspaceConverter.dtoToModel(bookingDTO.workspace),
            id = bookingDTO.id,
            beginBooking = Instant.ofEpochMilli(bookingDTO.beginBooking),
            endBooking = Instant.ofEpochMilli(bookingDTO.endBooking),
            recurrence = recurrenceModel
        )
    }
}