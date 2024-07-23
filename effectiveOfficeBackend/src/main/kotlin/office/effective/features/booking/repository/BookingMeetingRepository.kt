package office.effective.features.booking.repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.constants.BookingConstants
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.UnavailableDeleteEventException
import office.effective.common.exception.WorkspaceUnavailableException
import office.effective.features.booking.converters.GoogleCalendarConverter
import office.effective.features.booking.converters.toGoogleDateTime
import office.effective.features.calendar.repository.CalendarIdsRepository
import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.UserRepository
import office.effective.model.Booking
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

/**
 * Class that executes Google calendar queries for booking meeting rooms
 *
 * Filters out all events that have a start less than the calendar.minTime from application.conf
 */
class BookingMeetingRepository(
    private val calendarIdsRepository: CalendarIdsRepository,
    private val userRepository: UserRepository,
    private val calendar: Calendar,
    private val googleCalendarConverter: GoogleCalendarConverter
) : IBookingRepository {
    private val calendarEvents = calendar.Events()
    private val defaultCalendar = BookingConstants.DEFAULT_CALENDAR
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Finds workspace calendar id by workspace id
     *
     * @param workspaceId
     * @return calendar id by workspace id in database
     */
    private fun getCalendarIdByWorkspace(workspaceId: UUID): String {
        return try {
            calendarIdsRepository.findByWorkspace(workspaceId)
        } catch (e: InstanceNotFoundException) {
            defaultCalendar
        }
    }

    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     */
    override fun existsById(id: String): Boolean {
        logger.debug("[existsById] checking whether a booking with id={} exists", id)
        return findByCalendarIdAndBookingId(id) != null
    }

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     */
    override fun deleteById(id: String) {
        logger.debug("[deleteById] deleting the booking with id={}", id)

        findByCalendarIdAndBookingId(id) ?: throw UnavailableDeleteEventException(
            "Booking with id $id, that not found in defaultCalendar, can't be deleted"
        )

        try {
            calendarEvents.delete(defaultCalendar, id).execute() //We can't delete directly from workspace calendar
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
     */
    override fun findById(bookingId: String): Booking? {
        logger.debug("[findById] retrieving a booking with id={}", bookingId)
        val calendars: List<String> = calendarIdsRepository.findAllCalendarsId()

        val futures = calendars.map { calendarId ->
            findEventAsync(bookingId, calendarId)
        }

        val event: Event?  = CompletableFuture.allOf(*futures.toTypedArray())
            .thenApply {
                futures.map { it.get() }
            }
            .join()
            .find { it != null }

        if (event != null) {
            val booking = googleCalendarConverter.toMeetingWorkspaceBooking(event)
            if (!booking.isDeclinedByOwner) {
                return booking
            }
        }
        return null
    }

    private fun findEventAsync(eventId: String, calendarId: String): CompletableFuture<Event> {
        return CompletableFuture.supplyAsync {
            findByCalendarIdAndBookingId(eventId, calendarId)
        }
    }

    /**
     * Retrieves an event by calendar id and booking id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @param calendarId the calendar in which to search for the event
     * @return [Event] with the given [bookingId] from calendar with [calendarId]
     * or null if event with the given id doesn't exist
     */
    private fun findByCalendarIdAndBookingId(
        bookingId: String,
        calendarId: String = defaultCalendar
    ): Event? {
        logger.trace("Retrieving event from {} calendar by id", calendarId)
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
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions.
     * Should be a time in the default timezone.
     * @param timeMax upper bound (exclusive) for an event's start time to filter by.
     * Should be a time in the default timezone.
     * @param singleEvents Whether to expand recurring events into instances and only return single one-off
     * events and instances of recurring events, but not the underlying recurring events themselves.
     * @param calendarId
     */
    private fun basicQuery(
        timeMin: Long,
        timeMax: Long? = null,
        singleEvents: Boolean = true,
        calendarId: String = defaultCalendar
    ): Calendar.Events.List {
        return calendarEvents.list(calendarId)
            .setSingleEvents(singleEvents)
            .setTimeMin(timeMin.toGoogleDateTime())
            .setTimeMax(timeMax?.toGoogleDateTime())
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @return List of all workspace [Booking]
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
        val workspaceCalendarId = getCalendarIdByWorkspace(workspaceId)
        val eventsWithWorkspace = basicQuery(eventRangeFrom, eventRangeTo, returnInstances, workspaceCalendarId)
            .execute().items

        return filterAndConvertToBookings(eventsWithWorkspace)
    }

    /**
     * Checks whether the given event organised by user with the given email.
     * Organiser email may be specified at [Event.description] or [Event.organizer].email
     *
     * @param event
     * @param email
     * @return List of all user [Booking]
     */
    private fun checkEventOrganizer(event: Event, email: String): Boolean {
        if (event.organizer?.email == defaultCalendar) {
            return event.description.contains(email)
        }
        //TODO: if event was created by defaultCalendar account, but not from Effective Office, this method will return false
        return event.organizer?.email == email
    }

    /**
     * Retrieves user email from database by user id
     *
     * @param id
     * @return user email
     * @throws InstanceNotFoundException if user with the given id doesn't exist in database
     */
    private fun findUserEmailByUserId(id: UUID): String {
        return userRepository.findById(id)?.email ?: throw InstanceNotFoundException(
            UserEntity::class, "User with id $id not found"
        )
    }

    private fun getEventsWithQParam(
        calendarIds: List<String>,
        q: String,
        singleEvents: Boolean,
        eventRangeFrom: Long,
        eventRangeTo: Long?
    ): List<Event> {
        val executor = Executors.newFixedThreadPool(calendarIds.size)
        val futures = calendarIds.map { calendarId ->
            CompletableFuture.supplyAsync {
                basicQuery(
                    timeMin = eventRangeFrom,
                    timeMax = eventRangeTo,
                    singleEvents = singleEvents,
                    calendarId = calendarId
                ).setQ(q)
                    .execute().items
            }
        }
        val allEvents = CompletableFuture.allOf(*futures.toTypedArray())
            .thenApply {
                futures.map { it.get() }
            }
            .join()
        executor.shutdown()

        return allEvents.flatten()
    }

    private fun getAllEvents(
        calendarIds: List<String>,
        singleEvents: Boolean,
        eventRangeFrom: Long,
        eventRangeTo: Long?
    ): List<Event> {
        val executor = Executors.newFixedThreadPool(calendarIds.size)
        val futures = calendarIds.map { calendarId ->
            CompletableFuture.supplyAsync {
                basicQuery(
                    timeMin = eventRangeFrom,
                    timeMax = eventRangeTo,
                    singleEvents = singleEvents,
                    calendarId = calendarId
                ).execute().items
            }
        }
        val allEvents = CompletableFuture.allOf(*futures.toTypedArray())
            .thenApply {
                futures.map { it.get() }
            }
            .join()
        executor.shutdown()

        return allEvents.flatten()
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
        val userEmail: String = findUserEmailByUserId(ownerId)
        val calendars: List<String> = calendarIdsRepository.findAllCalendarsId()

        val eventsWithUser = getEventsWithQParam(calendars, userEmail, returnInstances, eventRangeFrom, eventRangeTo)

        val additionalFilter: (Event) -> Boolean = { event -> checkEventOrganizer(event, userEmail) }
        return filterAndConvertToBookings(eventsWithUser, additionalFilter)
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
        val userEmail: String = findUserEmailByUserId(ownerId)
        val workspaceCalendarId = getCalendarIdByWorkspace(workspaceId)

        val eventsWithUserAndWorkspace = basicQuery(eventRangeFrom, eventRangeTo, returnInstances, workspaceCalendarId)
            .setQ(userEmail)
            .execute().items

        val additionalFilter: (Event) -> Boolean = { event -> checkEventOrganizer(event, userEmail) }
        return filterAndConvertToBookings(eventsWithUserAndWorkspace, additionalFilter)
    }

    /**
     * Retrieves all bookings
     *
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @param returnInstances return recurring bookings as non-recurrent instances
     * @return All [Booking]s
     */
    override fun findAll(eventRangeFrom: Long, eventRangeTo: Long?, returnInstances: Boolean): List<Booking> {
        logger.debug(
            "[findAll] retrieving all bookings in range from {} to {}",
            Instant.ofEpochMilli(eventRangeFrom),
            eventRangeTo?.let { Instant.ofEpochMilli(it) } ?: "infinity"
        )
        val calendars: List<String> = calendarIdsRepository.findAllCalendarsId()
        val events: List<Event> = getAllEvents(calendars, returnInstances, eventRangeFrom, eventRangeTo)

        val result: MutableList<Booking> = mutableListOf()
        for (event in events) {
            val booking = googleCalendarConverter.toMeetingWorkspaceBooking(event)
            if (!booking.isDeclinedByOwner) {
                result.add(booking)
            }
        }
        return filterAndConvertToBookings(events)
    }

    private fun filterAndConvertToBookings(
        events: List<Event>,
        additionalFilter: (Event) -> Boolean = { true }
    ): List<Booking> {
        val result = mutableListOf<Booking>()
        for (event in events) {
            if (additionalFilter(event)) {
                val booking = googleCalendarConverter.toMeetingWorkspaceBooking(event)
                if (!booking.isDeclinedByOwner) {
                    result.add(googleCalendarConverter.toMeetingWorkspaceBooking(event))
                } else {
                    logger.trace("[filterAndConvertToBookings] filtered out event deleted by owner: {}", event)
                }
            }  else {
                logger.trace("[filterAndConvertToBookings] filtered out event with wrong organizer: {}", event)
            }
        }
        return result
    }

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     */
    override fun save(booking: Booking): Booking {
        logger.debug("[save] saving booking of workspace with id {}", booking.workspace.id)
        logger.trace("[save] booking to save: {}", booking)
        val workspaceCalendar = calendarIdsRepository.findByWorkspace(
            booking.workspace.id ?: throw MissingIdException("Missing workspace id")
        )
        val savedEvent: Event = if (booking.recurrence != null) {
            saveRecurringEvent(booking, workspaceCalendar)
        } else {
            saveSingleEvent(booking, workspaceCalendar)
        }
        return googleCalendarConverter.toMeetingWorkspaceBooking(
            event = savedEvent,
            owner = booking.owner,
            workspace = booking.workspace,
            participants = booking.participants
        ).also { savedBooking ->
                logger.trace("[save] saved booking: {}", savedBooking)
            }
    }

    /**
     * Saving booking without recurrence. Checks collision before saving an event.
     */
    private fun saveSingleEvent(booking: Booking, workspaceCalendar: String): Event {
        val event = googleCalendarConverter.toGoogleWorkspaceMeetingEvent(booking)
        if (singleEventHasCollision(event, workspaceCalendar)) {
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} " +
                    "unavailable at time between ${booking.beginBooking} and ${booking.endBooking}")
        }
        return calendar.Events().insert(defaultCalendar, event).execute()
    }

    /**
     * Saving booking with recurrence. Checks collision for all event instances after its saving.
     *
     * @param booking updated booking to be saved
     * @param workspaceCalendar calendar id for saving
     */
    private fun saveRecurringEvent(booking: Booking, workspaceCalendar: String): Event {
        val event = googleCalendarConverter.toGoogleWorkspaceMeetingEvent(booking)

        val savedEvent = calendar.Events().insert(defaultCalendar, event).execute()

        if (recurringEventHasCollision(savedEvent, workspaceCalendar)) {
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
        val workspaceCalendar = calendarIdsRepository.findByWorkspace(
            booking.workspace.id ?: throw MissingIdException("Missing workspace id")
        )
        val updatedEvent: Event = if (booking.recurrence != null) {
            updateRecurringEvent(booking, workspaceCalendar)
        } else {
            updateSingleEvent(booking, workspaceCalendar)
        }
        return googleCalendarConverter.toMeetingWorkspaceBooking(
            event = updatedEvent,
            owner = booking.owner,
            workspace = booking.workspace,
            participants = booking.participants
        ).also { updatedBooking ->
                logger.trace("[update] updated booking: {}", updatedBooking)
            }
    }

    /**
     * Updating booking without recurrence. Checks collision before updating an event.
     */
    private fun updateSingleEvent(booking: Booking, workspaceCalendar: String): Event {
        val eventOnUpdate = googleCalendarConverter.toGoogleWorkspaceMeetingEvent(booking)

        if (singleEventHasCollision(eventOnUpdate, workspaceCalendar)) {
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} " +
                    "unavailable at time between ${booking.beginBooking} and ${booking.endBooking}")
        }

        val bookingId = booking.id ?: throw MissingIdException("Booking model must have an id for update request")
        val prevEventVersion = findByCalendarIdAndBookingId(bookingId) ?: throw InstanceNotFoundException(
            WorkspaceBookingEntity::class, "Booking with id $bookingId not wound"
        )
        logger.trace("[updateSingleEvent] previous version of event: {}", prevEventVersion)

        return calendarEvents.update(defaultCalendar, bookingId, eventOnUpdate).execute()
    }

    /**
     * Updating booking with recurrence. Checks collision for all event instances after its update.
     *
     * @param booking updated booking to be saved
     * @param workspaceCalendar calendar id for saving
     */
    private fun updateRecurringEvent(booking: Booking, workspaceCalendar: String): Event {
        val bookingId = booking.id ?: throw MissingIdException("Update model must have id")
        val prevEventVersion = findByCalendarIdAndBookingId(bookingId) ?: throw InstanceNotFoundException(
            WorkspaceBookingEntity::class, "Booking with id $bookingId not wound"
        )
        logger.trace("[updateRecurringEvent] previous version of event: {}", prevEventVersion)
        val eventOnUpdate = googleCalendarConverter.toGoogleWorkspaceMeetingEvent(booking)

        val updatedEvent: Event = calendarEvents.update(defaultCalendar, bookingId, eventOnUpdate).execute()

        val sequence = updatedEvent.sequence
        if (recurringEventHasCollision(updatedEvent, workspaceCalendar)) {
            prevEventVersion.sequence = sequence
            calendarEvents.update(defaultCalendar, bookingId, prevEventVersion).execute()
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} unavailable at specified time.")
        }
        return updatedEvent
    }

    /**
     * Checks whether a non-recurring event has a collision with other events.
     *
     * @param eventToVerify event for collision check
     * @return True if event has a collision
     * */
    private fun singleEventHasCollision(eventToVerify: Event, workspaceCalendar: String): Boolean {
        val sameTimeEvents = calendarEvents.list(workspaceCalendar)
            .setSingleEvents(true)
            .setTimeMin(eventToVerify.start.dateTime)
            .setTimeMax(eventToVerify.end.dateTime)
            .execute().items

        for (event in sameTimeEvents) {
            if (areEventsOverlap(eventToVerify, event) &&
                eventsIsNotSame(eventToVerify, event) &&
                eventIsNotDeletedByOwner(event)) {
                return true
            }
        }
        return false
    }

    /**
     * Checks whether the saved recurring event has a collision with other events.
     *
     * @param incomingEvent must take only SAVED event
     * @return True if event has a collision and should be deleted
     * */
    private fun recurringEventHasCollision(incomingEvent: Event, workspaceCalendar: String): Boolean {
        logger.debug(
            "[checkBookingAvailable] checking if workspace with calendar id={} available for event {}",
            workspaceCalendar,
            incomingEvent
        )

        var result = false
        //TODO: Check, if we can receive instances without pushing this event into calendar
        val instances = calendarEvents.instances(workspaceCalendar, incomingEvent.id)
            .setMaxResults(50)
            .execute().items

        for (instance in instances) {
            if (singleEventHasCollision(instance, workspaceCalendar)) {
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
        var eventsDoNotBelongToSameRecurringEvent = true
        if (firstEvent.recurringEventId != null || secondEvent.recurringEventId != null) {
            eventsDoNotBelongToSameRecurringEvent = firstEvent.id != secondEvent.recurringEventId &&
                    firstEvent.recurringEventId != secondEvent.id &&
                    firstEvent.recurringEventId != secondEvent.recurringEventId
        }
        return firstEvent.id != secondEvent.id && eventsDoNotBelongToSameRecurringEvent
    }

    private fun eventIsNotDeletedByOwner(event: Event): Boolean {
        val organiserEmail = findOrganiser(event)
        for (attendee in event.attendees) {
            if (attendee.email == organiserEmail && attendee.responseStatus == "declined") {
                return false
            }
        }
        return true
    }

    private fun findOrganiser(event: Event): String {
        val organizer: String = event.organizer?.email ?: ""
        return if (organizer != BookingConstants.DEFAULT_CALENDAR) {
            organizer
        } else {
            event.description?.substringBefore(" ") ?: ""
        }
    }
}