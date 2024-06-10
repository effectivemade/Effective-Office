package office.effective.features.booking.converters

import model.RecurrenceDTO
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.utils.UuidValidator
import office.effective.dto.BookingDTO
import office.effective.dto.BookingRequestDTO
import office.effective.dto.BookingResponseDTO
import office.effective.dto.UserDTO
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.converters.WorkspaceDtoModelConverter
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.*
import org.slf4j.LoggerFactory
import java.time.Instant

/**
 * Converts between [BookingDTO] and [Booking]
 *
 * Uses [UserDTOModelConverter] and [WorkspaceDtoModelConverter] to convert contained users and workspaces
 */
class BookingDtoModelConverter(
    private val userConverter: UserDTOModelConverter,
    private val workspaceConverter: WorkspaceDtoModelConverter,
    private val userRepository: UserRepository,
    private val workspaceRepository: WorkspaceRepository,
    private val uuidValidator: UuidValidator
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Converts [Booking] to [BookingDTO]
     *
     * @param booking [Booking] to be converted
     * @return The resulting [BookingDTO] object
     */
    @Deprecated(
        message = "Deprecated since 1.0 api version",
        replaceWith = ReplaceWith("modelToResponseDto(booking)")
    )
    fun modelToDto(booking: Booking): BookingDTO {
        logger.trace("Converting booking model to dto")
        var recurrenceDTO : RecurrenceDTO? = null
        if (booking.recurrence != null) {
            recurrenceDTO = RecurrenceConverter.modelToDto(booking.recurrence!!)
        }
        return BookingDTO(
            owner = booking.owner?.let { userConverter.modelToDTO(it) }
                ?: UserDTO("", "", true, "", "", listOf(), "", ""),
            participants = booking.participants.map { userConverter.modelToDTO(it) },
            workspace = workspaceConverter.modelToResponseDto(booking.workspace),
            id = booking.id.toString(),
            beginBooking = booking.beginBooking.toEpochMilli(),
            endBooking = booking.endBooking.toEpochMilli(),
            recurrence = recurrenceDTO
        )
    }

    /**
     * Converts [Booking] to [BookingResponseDTO]
     *
     * @throws [MissingIdException] if [booking] doesn't contain an id
     * @param booking [Booking] to be converted
     * @return The resulting [BookingResponseDTO] object
     */
    fun modelToResponseDto(booking: Booking): BookingResponseDTO {
        logger.trace("Converting booking model to response dto")
        val id = booking.id ?: throw MissingIdException("Booking response must contains an id, but it was missed")
        val owner = booking.owner?.let {
            owner -> userConverter.modelToDTO(owner)
        }
        val participants = booking.participants.map {
            participant -> userConverter.modelToDTO(participant)
        }
        val recurrence = booking.recurrence?.let {
            recurrenceModel -> RecurrenceConverter.modelToDto(recurrenceModel)
        }
        return BookingResponseDTO(
            owner = owner,
            participants = participants,
            workspace = workspaceConverter.modelToDto(booking.workspace),
            id = id,
            beginBooking = booking.beginBooking.toEpochMilli(),
            endBooking = booking.endBooking.toEpochMilli(),
            recurrence = recurrence,
            recurringBookingId = booking.recurringBookingId
        )
    }

    /**
     * Converts [BookingDTO] to [Booking]
     *
     * @param bookingDTO [BookingDTO] to be converted
     * @return The resulting [Booking] object
     */
    @Deprecated(
        message = "Deprecated since 1.0 api version",
        replaceWith = ReplaceWith("requestDtoToModel(booking)")
    )
    fun dtoToModel(bookingDTO: BookingDTO): Booking {
        logger.trace("Converting booking dto to model")
        var recurrenceModel : RecurrenceModel? = null
        if (bookingDTO.recurrence != null) {
            recurrenceModel = RecurrenceConverter.dtoToModel(bookingDTO.recurrence)
        }
        return Booking(
            owner = userConverter.dTOToModel(bookingDTO.owner),
            participants = bookingDTO.participants.map { userConverter.dTOToModel(it) },
            workspace = workspaceConverter.responseDtoToModel(bookingDTO.workspace),
            id = bookingDTO.id,
            beginBooking = Instant.ofEpochMilli(bookingDTO.beginBooking),
            endBooking = Instant.ofEpochMilli(bookingDTO.endBooking),
            recurrence = recurrenceModel
        )
    }

    /**
     * Converts [BookingDTO] to [Booking]. Users and workspace will be retrieved from database
     *
     * @param bookingDto [BookingDTO] to be converted
     * @param id booking id
     * @return The resulting [Booking] object
     * @throws [InstanceNotFoundException] if user with the given email or
     * workspace with the given id doesn't exist in database
     */
    fun requestDtoToModel(bookingDto: BookingRequestDTO, id: String? = null): Booking {
        logger.trace("Converting booking response dto to model")
        val recurrence = bookingDto.recurrence?.let {
            recurrenceDto -> RecurrenceConverter.dtoToModel(recurrenceDto)
        }
        val owner: UserModel? = findOwner(bookingDto.ownerEmail)
        val participants = findParticipants(bookingDto.participantEmails)
        val workspace = findWorkspace(bookingDto.workspaceId)
        return Booking(
            owner = owner,
            participants = participants,
            workspace = workspace,
            id = id,
            beginBooking = Instant.ofEpochMilli(bookingDto.beginBooking),
            endBooking = Instant.ofEpochMilli(bookingDto.endBooking),
            recurrence = recurrence,
        )
    }

    private fun findOwner(ownerEmail: String?): UserModel? {
        if (ownerEmail != null) {
            return userRepository.findByEmail(ownerEmail)
                ?: throw InstanceNotFoundException(UserModel::class, "User with email $ownerEmail not found")
        }
        return null
    }

    private fun findParticipants(participantEmails: List<String>): List<UserModel> {
        val users = userRepository.findAllByEmails(participantEmails)
        if (users.size < participantEmails.size) {
            throw InstanceNotFoundException(UserModel::class, "Participant not found")
        }
        return users
    }

    private fun findWorkspace(workspaceUuid: String): Workspace {
        return workspaceRepository.findById(
            uuidValidator.uuidFromString(workspaceUuid)
        ) ?: throw InstanceNotFoundException(Workspace::class, "Workspace with id $workspaceUuid not found")
    }
}