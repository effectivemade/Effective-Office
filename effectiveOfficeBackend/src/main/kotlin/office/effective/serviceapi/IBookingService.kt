package office.effective.serviceapi

import office.effective.common.exception.InstanceNotFoundException
import office.effective.model.*
import java.util.*

/**
 * Interface that provides methods for manipulating [Booking] objects.
 */
interface IBookingService {

    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     */
    fun existsById(id: String): Boolean

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     */
    fun deleteById(id: String)

    /**
     * Retrieves a booking model by its id
     *
     * @param id - booking id
     * @return [Booking] with the given [id] or null if workspace with the given id doesn't exist
     */
    fun findById(id: String): Booking?

    /**
     * Returns all bookings. Bookings can be filtered by owner and workspace id
     *
     * @param userId use to filter by booking owner id
     * @param workspaceId use to filter by booking workspace id
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @param bookingRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @param bookingRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * @throws InstanceNotFoundException if [UserModel] or [Workspace] with the given id doesn't exist in database
     */
    fun findAll(
        userId: UUID? = null,
        workspaceId: UUID? = null,
        returnInstances: Boolean = true,
        bookingRangeTo: Long? = null,
        bookingRangeFrom: Long
    ): List<Booking>

    /**
     * Saves a given booking. Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     */
    fun save(booking: Booking): Booking

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param booking changed booking
     * @return [Booking] after change saving
     */
    fun update(booking: Booking): Booking
}
