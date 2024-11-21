package office.effective.features.booking.facade

import io.ktor.server.plugins.*
import office.effective.common.constants.BookingConstants
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.dto.BookingDTO
import office.effective.model.Booking
import office.effective.model.Workspace
import office.effective.serviceapi.IBookingService

/**
 * Class used in routes to handle bookings requests.
 * Provides business transaction, data conversion and validation.
 *
 * In case of an error, the database transaction will be rolled back.
 */
class BookingFacade(
    private val bookingService: IBookingService,
    private val transactionManager: DatabaseTransactionManager,
    private val uuidValidator: UuidValidator,
    private val bookingConverter: BookingFacadeConverter
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
     * @return [BookingDTO] with the given id
     * @throws InstanceNotFoundException if booking with the given id doesn't exist in database
     * @author Daniil Zavyalov
     */
    fun findById(id: String): BookingDTO {
        val dto: BookingDTO = transactionManager.useTransaction({
            val model = bookingService.findById(id)
                ?: throw InstanceNotFoundException(Workspace::class, "Booking with id $id not found")
            bookingConverter.modelToDto(model)
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
     * @return [BookingDTO] list
     * @author Daniil Zavyalov
     */
    fun findAll(
        userId: String?,
        workspaceId: String?,
        bookingRangeTo: Long?,
        bookingRangeFrom: Long = BookingConstants.MIN_SEARCH_START_TIME
    ): List<BookingDTO> {
        if (bookingRangeTo != null && bookingRangeTo <= bookingRangeFrom) {
            throw BadRequestException("Max booking start time should be null or greater than min start time")
        }
        val bookingList: List<Booking> = transactionManager.useTransaction({
            bookingService.findAll(
                userId?.let { uuidValidator.uuidFromString(it) },
                workspaceId?.let { uuidValidator.uuidFromString(it) },
                bookingRangeTo,
                bookingRangeFrom
            )
        })
        return bookingList.map {
            bookingConverter.modelToDto(it)
        }
    }

    /**
     * Saves a given booking. Use the returned model for further operations
     *
     * @param bookingDTO [BookingDTO] to be saved
     * @return saved [BookingDTO]
     * @author Daniil Zavyalov
     */
    fun post(bookingDTO: BookingDTO): BookingDTO {
        val model = bookingConverter.dtoToModel(bookingDTO)
        val dto: BookingDTO = transactionManager.useTransaction({
            val savedModel = bookingService.save(model)
            bookingConverter.modelToDto(savedModel)
        })
        return dto
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param bookingDTO changed booking
     * @return [BookingDTO] after change saving
     * @throws BadRequestException if booking id is null
     * @author Daniil Zavyalov
     */
    fun put(bookingDTO: BookingDTO): BookingDTO {
        if (bookingDTO.id == null) throw BadRequestException("Missing booking id")
        val model = bookingConverter.dtoToModel(bookingDTO)
        val dto: BookingDTO = transactionManager.useTransaction({
            val savedModel = bookingService.update(model)
            bookingConverter.modelToDto(savedModel)
        })
        return dto
    }
}