package office.effective.features.booking.converters

import model.RecurrenceDTO
import office.effective.common.exception.InstanceNotFoundException
import office.effective.dto.BookingDTO
import office.effective.dto.BookingRequestDTO
import office.effective.dto.BookingResponseDTO
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.*
import org.slf4j.LoggerFactory
import java.time.Instant

/**
 * Converts between [BookingDTO] and [Booking]
 *
 * Uses [UserDTOModelConverter] and [WorkspaceFacadeConverter] to convert contained users and workspaces
 */
class BookingFacadeConverter(
    private val userConverter: UserDTOModelConverter,
    private val workspaceConverter: WorkspaceFacadeConverter,
    private val userRepository: UserRepository,
    private val workspaceRepository: WorkspaceRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Converts [Booking] to [BookingDTO]
     *
     * @param booking [Booking] to be converted
     * @return The resulting [BookingDTO] object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    @Deprecated(
        message = "Deprecated since 1.0 api version",
        replaceWith = ReplaceWith("modelToResponseDto(booking)")
    )
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
     * Converts [Booking] to [BookingResponseDTO]
     *
     * @param booking [Booking] to be converted
     * @return The resulting [BookingResponseDTO] object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun modelToResponseDto(booking: Booking): BookingResponseDTO {
        logger.trace("Converting booking model to response dto")
        var recurrenceDTO : RecurrenceDTO? = null
        if(booking.recurrence != null) {
            recurrenceDTO = RecurrenceConverter.modelToDto(booking.recurrence!!)
        }
        return BookingResponseDTO(
            owner = userConverter.modelToDTO(booking.owner),
            participants = booking.participants.map { userConverter.modelToDTO(it) },
            workspace = workspaceConverter.modelToDto(booking.workspace),
            id = booking.id.toString(),
            beginBooking = booking.beginBooking.toEpochMilli(),
            endBooking = booking.endBooking.toEpochMilli(),
            recurrence = recurrenceDTO,
            recurringBookingId = booking.recurringBookingId
        )
    }

    /**
     * Converts [BookingDTO] to [Booking]
     *
     * @param bookingDTO [BookingDTO] to be converted
     * @return The resulting [Booking] object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    @Deprecated(
        message = "Deprecated since 1.0 api version",
        replaceWith = ReplaceWith("requestDtoToModel(booking)")
    )
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

    /**
     * Converts [BookingDTO] to [Booking]
     *
     * @param bookingDTO [BookingDTO] to be converted
     * @return The resulting [Booking] object
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun requestDtoToModel(bookingDto: BookingRequestDTO, id: String? = null): Booking {
        logger.trace("Converting booking response dto to model")
        var recurrenceModel : RecurrenceModel? = null
        if(bookingDto.recurrence != null) {
            recurrenceModel = RecurrenceConverter.dtoToModel(bookingDto.recurrence)
        }
        val owner: UserModel? = findOwner(bookingDto.ownerEmail)
        val participants = findParticipants()
        return Booking(
            owner = owner,
            participants = bookingDto.participants.map { userConverter.dTOToModel(it) },
            workspace = workspaceConverter.dtoToModel(bookingDto.workspace),
            id = id,
            beginBooking = Instant.ofEpochMilli(bookingDto.beginBooking),
            endBooking = Instant.ofEpochMilli(bookingDto.endBooking),
            recurrence = recurrenceModel,
        )
    }

    private fun findOwner(ownerEmail: String?): UserModel? {
        if (ownerEmail != null) {
            return userRepository.findByEmail(ownerEmail)
                ?: throw InstanceNotFoundException(UserModel::class, "User with email $ownerEmail not found")
        }
        return null;
    }

    private fun findParticipants(participantEmails: List<String>): List<UserModel> {

    }
}