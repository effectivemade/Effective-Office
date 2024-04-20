package office.effective.features.booking.repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.constants.BookingConstants
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.WorkspaceUnavailableException
import office.effective.features.booking.converters.GoogleCalendarConverter
import office.effective.model.Booking
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*

/**
 * Class that executes Google calendar queries for booking regular workspaces
 *
 * Filters out all events that have a start less than the calendar.minTime from application.conf
 */
class BookingRegularRepository(
    private val calendar: Calendar,
    private val googleCalendarConverter: GoogleCalendarConverter,
) : IBookingRepository {
    private val calendarEvents = calendar.Events()
    private val regularWorkspacesCalendar: String = BookingConstants.REGULAR_WORKSPACES_CALENDAR
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     * @author Daniil Zavyalov
     */
    override fun existsById(id: String): Boolean {
        logger.debug("[existsById] checking whether a booking with id={} exists", id)
        val event: Any? = findByCalendarIdAndBookingId(id)
        return event != null
    }

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     * @author Daniil Zavyalov
     */
    override fun deleteById(id: String) {
        logger.debug("[deleteById] deleting the booking with id={}", id)
        try {
            calendarEvents.delete(regularWorkspacesCalendar, id).execute()
        } catch (e: GoogleJsonResponseException) {
            if (e.statusCode != 404 && e.statusCode != 410) {
                throw e
            }
        }
    }

    /**
     * Retrieves a booking model by its id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @return [Booking] with the given [bookingId] or null if booking with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    override fun findById(bookingId: String): Booking? {
        logger.debug("[findById] retrieving a booking with id={}", bookingId)
        val event: Event? = findByCalendarIdAndBookingId(bookingId)
        logger.trace("[findById] request to Google Calendar completed")
        return event?.let { googleCalendarConverter.toWorkspaceBooking(it) }
    }

    /**
     * Retrieves an event by calendar id and booking id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @param calendarId the calendar in which to search for the event
     * @return [Event] with the given [bookingId] from calendar with [calendarId]
     * or null if event with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    private fun findByCalendarIdAndBookingId(bookingId: String, calendarId: String = regularWorkspacesCalendar): Event? {
        return try {
            calendar.events().get(calendarId, bookingId).execute()
        } catch (e: GoogleJsonResponseException) {
            if (e.statusCode == 404) return null
            else throw e
        }
    }

    /**
     * Request template containing all required parameters
     *
     * @param timeMin lover bound for filtering bookings by start time.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param timeMax
     * @param singleEvents
     * @param calendarId
     * @author Daniil Zavyalov
     */
    private fun basicQuery(
        timeMin: Long,
        timeMax: Long? = null,
        singleEvents: Boolean = true,
        calendarId: String = regularWorkspacesCalendar
    ): Calendar.Events.List {
        return calendarEvents.list(calendarId)
            .setSingleEvents(singleEvents)
            .setTimeMin(DateTime(timeMin))
            .setTimeMax(timeMax?.let { DateTime(it) })
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return List of all workspace [Booking]
     * @author Daniil Zavyalov
     */
    override fun findAllByWorkspaceId(
        workspaceId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long?,
        returnInstances: Boolean
    ): List<Booking> {
        logger.debug(
            "[findAllByWorkspaceId] retrieving all bookings for workspace with id={} in range from {} to {}",
            workspaceId,
            Instant.ofEpochMilli(eventRangeFrom),
            eventRangeTo?.let { Instant.ofEpochMilli(it) } ?: "infinity"
        )
        val eventsWithWorkspace = basicQuery(eventRangeFrom, eventRangeTo, returnInstances)
            .setQ(workspaceId.toString())
            .execute().items
        logger.trace("[findAllByWorkspaceId] request to Google Calendar completed")

        return eventsWithWorkspace.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
    }


    /**
     * Returns all bookings with the given owner id
     *
     * @param ownerId
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return List of all user [Booking]
     * @throws InstanceNotFoundException if user with the given id doesn't exist in database
     * @author Daniil Zavyalov
     */
    override fun findAllByOwnerId(
        ownerId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long?,
        returnInstances: Boolean
    ): List<Booking> {
        logger.debug(
            "[findAllByOwnerId] retrieving all bookings for user with id={} in range from {} to {}",
            ownerId,
            Instant.ofEpochMilli(eventRangeFrom),
            eventRangeTo?.let { Instant.ofEpochMilli(it) } ?: "infinity"
        )
        val eventsWithUser = basicQuery(eventRangeFrom, eventRangeTo, returnInstances)
            .setQ(ownerId.toString())
            .execute().items
        logger.trace("[findAllByOwnerId] request to Google Calendar completed")

        return eventsWithUser.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
    }

    /**
     * Returns all bookings with the given workspace and owner id
     *
     * @param ownerId
     * @param workspaceId
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return List of all [Booking]s with the given workspace and owner id
     * @author Daniil Zavyalov
     */
    override fun findAllByOwnerAndWorkspaceId(
        ownerId: UUID,
        workspaceId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long?,
        returnInstances: Boolean
    ): List<Booking> {
        logger.debug(
            "[findAllByOwnerAndWorkspaceId] retrieving all bookings for a workspace with id={} created by user with id={} in range from {} to {}",
            workspaceId,
            ownerId,
            Instant.ofEpochMilli(eventRangeFrom),
            eventRangeTo?.let { Instant.ofEpochMilli(it) } ?: "infinity"
        )
        val eventsWithUserAndWorkspace = basicQuery(eventRangeFrom, eventRangeTo, returnInstances)
            .setQ("$workspaceId $ownerId")
            .execute().items
        logger.trace("[findAllByOwnerAndWorkspaceId] request to Google Calendar completed")

        return eventsWithUserAndWorkspace.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
    }

    /**
     * Retrieves all bookings
     *
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return All [Booking]s
     * @author Daniil Zavyalov
     */
    override fun findAll(eventRangeFrom: Long, eventRangeTo: Long?, returnInstances: Boolean): List<Booking> {
        logger.debug(
            "[findAll] retrieving all bookings in range from {} to {}",
            Instant.ofEpochMilli(eventRangeFrom),
            eventRangeTo?.let { Instant.ofEpochMilli(it) } ?: "infinity"
        )
        val events = basicQuery(eventRangeFrom, eventRangeTo, returnInstances).execute().items
        logger.trace("[findAll] request to Google Calendar completed")

        return events.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
    }

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     */
    override fun save(booking: Booking): Booking {
        logger.debug("[save] saving booking of regular workspace with id {}", booking.workspace.id)
        logger.trace("[save] regular workspace booking to save: {}", booking)
        val savedEvent: Event = if (booking.recurrence != null) {
            saveRecurringEvent(booking)
        } else {
            saveSingleEvent(booking)
        }
        return googleCalendarConverter.toWorkspaceBooking(savedEvent)
            .also { savedBooking ->
                logger.trace("[save] saved booking: {}", savedBooking)
            }
    }

    /**
     * Saving booking without recurrence. Checks collision before saving an event.
     */
    private fun saveSingleEvent(booking: Booking): Event {
        val workspaceId = booking.workspace.id ?: throw MissingIdException("Missing booking workspace id")
        val event = googleCalendarConverter.toGoogleWorkspaceMeetingEvent(booking)
        if (singleEventHasCollision(event, workspaceId)) {
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} " +
                    "unavailable at time between ${booking.beginBooking} and ${booking.endBooking}")
        }
        return calendar.Events().insert(regularWorkspacesCalendar, event).execute()
    }

    /**
     * Saving booking with recurrence. Checks collision for all event instances after its saving.
     *
     * @param booking updated booking to be saved
     */
    private fun saveRecurringEvent(booking: Booking): Event {
        val workspaceId = booking.workspace.id ?: throw MissingIdException("Missing booking workspace id")
        val event = googleCalendarConverter.toGoogleWorkspaceMeetingEvent(booking)

        val savedEvent = calendar.Events().insert(regularWorkspacesCalendar, event).execute()

        if (recurringEventHasCollision(savedEvent, workspaceId)) {
            deleteById(savedEvent.id)
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} unavailable at specified time.")
        }
        return savedEvent
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param booking changed booking
     * @return [Booking] after change saving
     * @throws MissingIdException if [Booking.id] or [Booking.workspace].id is null
     * @throws InstanceNotFoundException if booking given id doesn't exist in the database
     * @throws WorkspaceUnavailableException if booking unavailable because of collision check
     */
    override fun update(booking: Booking): Booking {
        logger.debug("[update] updating booking of workspace with id {}", booking.id)
        logger.trace("[update] new booking: {}", booking)

        val updatedEvent: Event = if (booking.recurrence != null) {
            updateRecurringEvent(booking)
        } else {
            updateSingleEvent(booking)
        }
        return googleCalendarConverter.toBookingModelForMeetingWorkspace(updatedEvent)
            .also { updatedBooking ->
                logger.trace("[update] updated booking: {}", updatedBooking)
            }
    }

    /**
     * Updating booking without recurrence. Checks collision before updating an event.
     */
    private fun updateSingleEvent(booking: Booking): Event {
        val workspaceId = booking.workspace.id ?: throw MissingIdException("Missing booking workspace id")
        val eventOnUpdate = googleCalendarConverter.toGoogleWorkspaceRegularEvent(booking)
        if (singleEventHasCollision(eventOnUpdate, workspaceId)) {
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} " +
                    "unavailable at time between ${booking.beginBooking} and ${booking.endBooking}")
        }

        val bookingId = booking.id ?: throw MissingIdException("Booking model must have an id for update request")
        val prevEventVersion = findByCalendarIdAndBookingId(bookingId) ?: throw InstanceNotFoundException(
            WorkspaceBookingEntity::class, "Booking with id $bookingId not wound"
        )
        logger.trace("[updateSingleEvent] previous version of event: {}", prevEventVersion)

        return calendarEvents.update(regularWorkspacesCalendar, bookingId, eventOnUpdate).execute()
    }

    /**
     * Updating booking with recurrence. Checks collision for all event instances after its update.
     *
     * @param booking updated booking to be saved
     */
    private fun updateRecurringEvent(booking: Booking): Event {
        val bookingId = booking.id ?: throw MissingIdException("Update model must have id")
        val workspaceId = booking.workspace.id ?: throw MissingIdException("Missing booking workspace id")
        val prevEventVersion = findByCalendarIdAndBookingId(bookingId) ?: throw InstanceNotFoundException(
            WorkspaceBookingEntity::class, "Booking with id $bookingId not wound"
        )
        logger.trace("[updateRecurringEvent] previous version of event: {}", prevEventVersion)
        val eventOnUpdate = googleCalendarConverter.toGoogleWorkspaceRegularEvent(booking)

        val updatedEvent: Event = calendarEvents.update(regularWorkspacesCalendar, bookingId, eventOnUpdate).execute()

        val sequence = updatedEvent.sequence
        if (recurringEventHasCollision(updatedEvent, workspaceId)) {
            prevEventVersion.sequence = sequence
            calendarEvents.update(regularWorkspacesCalendar, bookingId, prevEventVersion).execute()
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} unavailable at specified time.")
        }
        return updatedEvent
    }

    /**
     * Checks whether a non-recurring event has a collision with other events.
     *
     * @param eventToVerify event for collision check
     * @return True if event has a collision and can't be saved
     * */
    private fun singleEventHasCollision(eventToVerify: Event, workspaceId: UUID): Boolean {
        val sameTimeEvents = basicQuery(
            timeMin = eventToVerify.start.dateTime.value,
            timeMax = eventToVerify.end.dateTime.value,
            singleEvents = true,
        ).setQ(workspaceId.toString())
            .execute().items
        for (event in sameTimeEvents) {
            if (areEventsOverlap(eventToVerify, event) && eventsIsNotSame(eventToVerify, event)) {
                return true
            }
        }
        return false
    }

    /**
     * Checks whether the saved recurring event has a collision with other events.
     *
     * @param incomingEvent must take only SAVED event
     * @param workspaceId id of recurring workspace
     * @return True if event has a collision and should be deleted
     * */
    private fun recurringEventHasCollision(incomingEvent: Event, workspaceId: UUID): Boolean {
        logger.debug(
            "[checkBookingAvailable] checking if workspace with calendar id={} available for event {}",
            regularWorkspacesCalendar,
            incomingEvent
        )

        var result = false
        //TODO: Check, if we can receive instances without pushing this event into calendar
        val instances = calendarEvents.instances(regularWorkspacesCalendar, incomingEvent.id)
            .setMaxResults(50)
            .execute().items

        for (instance in instances) {
            if (singleEventHasCollision(instance, workspaceId)) {
                result = true
            }
        }
        logger.debug("[recurringEventHasCollision] result: {}", result)
        return result
    }

    /**
     * Checks whether events has collision
     */
    private fun areEventsOverlap(firstEvent: Event, secondEvent: Event): Boolean {
        return secondEvent.start.dateTime.value < firstEvent.end.dateTime.value
                && secondEvent.end.dateTime.value > firstEvent.start.dateTime.value
    }

    /**
     * Checks whether events aren't the same event or instances of the same event
     */
    private fun eventsIsNotSame(firstEvent: Event, secondEvent: Event): Boolean {
        return firstEvent.id != secondEvent.id &&
                firstEvent.id != secondEvent.recurringEventId &&
                firstEvent.recurringEventId != secondEvent.id &&
                (firstEvent.recurringEventId != firstEvent.recurringEventId || firstEvent.recurringEventId == null)
    }
}
