package office.effective.features.booking.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.model.Booking
import java.util.*

/**
 * Interface of repository to manipulate with workspace bookings
 */
interface IBookingRepository {
    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun existsById(id: String): Boolean

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun deleteById(id: String)

    /**
     * Retrieves a booking model by its id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @return [Booking] with the given [bookingId] or null if booking with the given id doesn't exist
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun findById(bookingId: String): Booking?

    /**
     * Returns all bookings with the given owner id
     *
     * @param ownerId
     * @param eventRangeTo use to set an upper bound for filtering bookings by start time
     * @param eventRangeFrom lover bound for filtering bookings by start time
     * @param returnInstances return recurring bookings as non-recurrent instances
     *
     * @return List of all user [Booking]
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun findAllByOwnerId(
        ownerId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long? = null,
        returnInstances: Boolean = true,
    ): List<Booking>

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @param eventRangeFrom use to set an upper bound for filtering bookings by start time
     * @param eventRangeTo lover bound for filtering bookings by start time
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return List of all workspace [Booking]
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun findAllByWorkspaceId(
        workspaceId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long? = null,
        returnInstances: Boolean = true,
    ): List<Booking>

    /**
     * Returns all bookings with the given workspace and owner id
     *
     * @param ownerId
     * @param workspaceId
     * @param eventRangeFrom use to set an upper bound for filtering bookings by start time
     * @param eventRangeTo lover bound for filtering bookings by start time
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return List of all [Booking]s with the given workspace and owner id
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun findAllByOwnerAndWorkspaceId(
        ownerId: UUID,
        workspaceId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long? = null,
        returnInstances: Boolean = true,
    ): List<Booking>

    /**
     * Retrieves all bookings
     *
     * @param eventRangeFrom use to set an upper bound for filtering bookings by start time
     * @param eventRangeTo lover bound for filtering bookings by start time
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return All [Booking]s
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun findAll(eventRangeFrom: Long, eventRangeTo: Long? = null, returnInstances: Boolean = true): List<Booking>

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun save(booking: Booking): Booking

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param booking changed booking
     * @return [Booking] after change saving
     * @throws InstanceNotFoundException if booking given id doesn't exist in the database
     * @author Daniil Zavyalov, Danil Kiselev
     */
    fun update(booking: Booking): Booking
}