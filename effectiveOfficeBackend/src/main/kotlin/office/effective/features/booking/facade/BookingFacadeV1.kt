package office.effective.features.booking.facade

import io.ktor.server.plugins.*
import office.effective.common.constants.BookingConstants
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.booking.converters.BookingDtoModelConverter
import office.effective.dto.BookingRequestDTO
import office.effective.dto.BookingResponseDTO
import office.effective.model.Booking
import office.effective.model.Workspace
import office.effective.serviceapi.IBookingService

/**
 * Class used in routes to handle bookings requests.
 * Provides business transaction, data conversion and validation.
 *
 * In case of an error, the database transaction will be rolled back.
 */
class BookingFacadeV1(
    private val bookingService: IBookingService,
    private val transactionManager: DatabaseTransactionManager,
    private val uuidValidator: UuidValidator,
    private val bookingConverter: BookingDtoModelConverter
) {

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     * @author Daniil Zavyalov
     */
    fun deleteById(id: String) {
        transactionManager.useTransaction({
            bookingService.deleteById(id)
        })
    }

    /**
     * Retrieves a booking model by its id
     *
     * @param id id of requested booking
     * @return [BookingResponseDTO] with the given id
     * @throws InstanceNotFoundException if booking with the given id doesn't exist in database
     * @author Daniil Zavyalov
     */
    fun findById(id: String): BookingResponseDTO {
        val dto: BookingResponseDTO = transactionManager.useTransaction({
            val model = bookingService.findById(id)
                ?: throw InstanceNotFoundException(Workspace::class, "Booking with id $id not found")
            bookingConverter.modelToResponseDto(model)
        })
        return dto
    }

    /**
     * Returns all bookings. Bookings can be filtered by owner and workspace id
     *
     * @param userId use to filter by booking owner id. Should be valid UUID
     * @param workspaceId use to filter by booking workspace id. Should be valid UUID
     * @param bookingRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * Should be greater than range_from.
     * @param bookingRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Should be lover than [bookingRangeFrom]. Default value: [BookingConstants.MIN_SEARCH_START_TIME]
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return [BookingResponseDTO] list
     * @author Daniil Zavyalov
     */
    fun findAll(
        userId: String?,
        workspaceId: String?,
        bookingRangeTo: Long?,
        bookingRangeFrom: Long = BookingConstants.MIN_SEARCH_START_TIME,
        returnInstances: Boolean
    ): List<BookingResponseDTO> {
        if (bookingRangeTo != null && bookingRangeTo <= bookingRangeFrom) {
            throw BadRequestException("Max booking start time must be null or greater than min start time")
        }
        val bookingList: List<Booking> = transactionManager.useTransaction({
            bookingService.findAll(
                userId?.let { uuidValidator.uuidFromString(it) },
                workspaceId?.let { uuidValidator.uuidFromString(it) },
                returnInstances,
                bookingRangeTo,
                bookingRangeFrom
            )
        })
        return bookingList.map { booking ->
            bookingConverter.modelToResponseDto(booking)
        }
    }

    /**
     * Saves a given booking. Use the returned model for further operations
     *
     * @param bookingDTO [BookingRequestDTO] to be saved
     * @return saved [BookingResponseDTO]
     * @author Daniil Zavyalov
     */
    fun post(bookingDTO: BookingRequestDTO): BookingResponseDTO {
        val model = bookingConverter.requestDtoToModel(bookingDTO)
        val dto: BookingResponseDTO = transactionManager.useTransaction({
            val savedModel = bookingService.save(model)
            bookingConverter.modelToResponseDto(savedModel)
        })
        return dto
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param bookingDTO changed booking
     * @param bookingId booking id
     * @return updated booking
     * @author Daniil Zavyalov
     */
    fun put(bookingDTO: BookingRequestDTO, bookingId: String): BookingResponseDTO {
        val model = bookingConverter.requestDtoToModel(bookingDTO, bookingId)
        val dto: BookingResponseDTO = transactionManager.useTransaction({
            val savedModel = bookingService.update(model)
            bookingConverter.modelToResponseDto(savedModel)
        })
        return dto
    }
}