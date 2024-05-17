package office.effective.features.booking.converters

import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Event.Organizer
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import office.effective.common.constants.BookingConstants
import office.effective.common.exception.UnavailableOperationException
import office.effective.common.utils.UuidValidator
import office.effective.dto.BookingDTO
import office.effective.dto.WorkspaceDTO
import office.effective.features.calendar.repository.CalendarIdsRepository
import office.effective.features.user.repository.UserRepository
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Workspace
import office.effective.features.booking.converters.RecurrenceRuleFactory.getRecurrence
import office.effective.features.booking.converters.RecurrenceRuleFactory.rule
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.RecurrenceModel.Companion.toRecurrence
import org.slf4j.LoggerFactory
import java.time.Instant
import java.util.*
import kotlin.collections.List as List

/**
 * Converts between Google Calendar [Event] and [BookingDTO] objects.
 */
class GoogleCalendarConverter(
    private val calendarIdsRepository: CalendarIdsRepository,
    private val userRepository: UserRepository,
    private val verifier: UuidValidator,
    private val workspaceRepository: WorkspaceRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val defaultAccount: String = BookingConstants.DEFAULT_CALENDAR

    /**
     * Converts regular [Event] to [Booking]
     *
     * Creates placeholders if workspace or owner doesn't exist in database
     *
     * @param event [Event] to be converted
     * @param owner specify this parameter to reduce the number
     * of database queries if the owner has already been retrieved
     * @param participants specify this parameter to reduce the number
     * of database queries if participants have already been retrieved
     * @param workspace specify this parameter to reduce the number
     * of database queries if workspace have already been retrieved
     * @return The resulting [Booking] object
     */
    fun toRegularWorkspaceBooking(
        event: Event,
        owner: UserModel? = null,
        workspace: Workspace? = null,
        participants: List<UserModel>? = null,
    ): Booking {
        logger.debug("[toRegularWorkspaceBooking] converting an event to workspace booking dto")
        val recurrence = event.recurrence?.toString()?.getRecurrence()

        val model = Booking(
            owner = owner ?: getUserModel(event),
            participants = participants ?: emptyList(),
            workspace = workspace ?: getWorkspaceModel(event),
            id = event.id ?: null,
            beginBooking = toLocalInstant(event.start),
            endBooking = toLocalInstant(event.end),
            recurrence = recurrence?.let { RecurrenceConverter.recurrenceToModel(it) },
            recurringBookingId = event.recurringEventId
        )
        logger.trace("[toRegularWorkspaceBooking] {}", model.toString())
        return model
    }

    /**
     * Find [UserModel]. May return placeholder if user with the given email doesn't exist in database
     *
     * @param event
     * @return [UserModel] with data from database or [UserModel] placeholder
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    private fun getUserModel(event: Event): UserModel {
        val userId: UUID = try {
            UUID.fromString(event.description.substringBefore(" "))
        } catch (e: Exception) {
            logger.error("[getUserModel] Can't get user UUID from Google Calendar event description. " +
                    "Creating placeholder", e)
            return UserModel(
                id = null,
                fullName = "Unregistered user",
                tag = null,
                active = false,
                role = null,
                avatarURL = null,
                integrations = emptySet(),
                email = "placeholder@gmail.com"
            )
        }
        val userModel: UserModel = userRepository.findById(userId)
            ?: run {
                logger.warn("[getUserModel] can't find a user with id ${userId}. Creating placeholder.")
                UserModel(
                    id = null,
                    fullName = "Unregistered user",
                    tag = null,
                    active = false,
                    role = null,
                    avatarURL = null,
                    integrations = emptySet(),
                    email = "placeholder@gmail.com"
                )
            }
        return userModel
    }

    /**
     * Find [Workspace] for regular workspace booking. May return placeholder
     *
     * @param event
     * @return [Workspace]
     */
    private fun getWorkspaceModel(event: Event): Workspace {
        val workspaceId: UUID = try {
            UUID.fromString(event.summary.substringBefore(" "))
        } catch (e: Exception) {
            logger.error("[getWorkspaceModel] Can't get workspace UUID from Google Calendar event description. " +
                    "Creating placeholder", e)
            return Workspace(null, "Nonexistent workspace", "regular", listOf(), null)
        }
        return workspaceRepository.findById(workspaceId) ?: run {
            logger.warn("[getWorkspaceModel] can't find a workspace with id ${workspaceId}. Creating placeholder.")
            Workspace(null, "Nonexistent workspace", "regular", listOf(), null)
        }
    }

    /**
     * Converts meeting [Event] to [Booking].
     * If participant responseStatus is declined that participant will be filtered out.
     *
     * @param event [Event] to be converted
     * @param owner specify this parameter to reduce the number
     * of database queries if the owner has already been retrieved
     * @param participants specify this parameter to reduce the number
     * of database queries if participants have already been retrieved
     * @param workspace specify this parameter to reduce the number
     * of database queries if workspace have already been retrieved
     * @return The resulting [BookingDTO] object
     * @author Danil Kiselev, Max Mishenko
     */
    fun toMeetingWorkspaceBooking(
        event: Event,
        owner: UserModel? = null,
        workspace: Workspace? = null,
        participants: List<UserModel>? = null,
    ): Booking {
        logger.debug("[toMeetingWorkspaceBooking] converting calendar event to meeting room booking model")
        val organizerEmail: String? = extractOrganizerEmailFromMeetingEvent(event);
        val recurrence = event.recurrence?.toString()?.getRecurrence()
        val participantModels = participants ?: getParticipantsModels(event)

        val booking = Booking(
            owner = owner ?: getUserModel(organizerEmail),
            participants = participantModels,
            workspace = workspace ?: getWorkspaceModel(getCalendarId(event)),
            id = event.id ?: null,
            beginBooking = toLocalInstant(event.start),
            endBooking = toLocalInstant(event.end),
            recurrence = recurrence?.let { RecurrenceConverter.recurrenceToModel(it) },
            isDeclinedByOwner = participantModels.none { model -> model.email == organizerEmail }
        )
        logger.trace("[toMeetingWorkspaceBooking] {}", booking.toString())
        return booking
    }

    private fun extractOrganizerEmailFromMeetingEvent(event: Event): String? {
        val organizerFromEvent: String? = event.organizer?.email
        val organizerFromDescription: String? = event.description?.substringBefore(" ")
        if (organizerFromEvent == defaultAccount || organizerFromEvent == null) {
            if (organizerFromDescription == null) {
                return null
            } else return organizerFromDescription
        } else return organizerFromEvent
    }

    /**
     * Find [UserModel] by email. May return placeholder if user with the given email doesn't exist in database
     *
     * @param email
     * @return [UserModel] with data from database or [UserModel] placeholder with the given [email]
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    private fun getUserModel(email: String?): UserModel? {
        if (email == null) {
            return null
        }
        val userModel: UserModel = userRepository.findByEmail(email)
            ?: run {
                logger.warn("[getUserModel] can't find a user with email ${email}. Creating placeholder.")
                UserModel(
                    id = null,
                    fullName = "Unregistered user",
                    tag = null,
                    active = false,
                    role = null,
                    avatarURL = null,
                    integrations = emptySet(),
                    email = email
                )
            }
        return userModel
    }

    /**
     * Gets the list of event participants, excluding resources, and returns a list of user Models.
     *
     * @param event The event for which participants need to be retrieved.
     * @return List of user Models.
     */
    private fun getParticipantsModels(event: Event): List<UserModel> {
        val attendees = event.attendees
            .filter { attendee -> !attendee.isResource && attendee.responseStatus != "declined" }
            .map { attendee -> attendee.email }
        return userRepository.findAllByEmails(attendees)
    }

    /**
     * Retrieves the calendar ID of the workspace from the event.
     * If the ID is not found, returns a default value with a warning log.
     *
     * @param event The event from which to retrieve the calendar ID.
     * @return Calendar ID of the workspace or default value.
     */
    private fun getCalendarId(event: Event): String? {
        return event.attendees
            ?.firstOrNull { it?.resource == true }
            ?.email
    }

    /**
     * Find [WorkspaceDTO] by workspace calendar id
     *
     * @param calendarId Google id of calendar of workspace
     * @return [WorkspaceDTO]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun getWorkspaceModel(calendarId: String?): Workspace {
        if (calendarId == null) {
            logger.warn("[toBookingDTO] can't get workspace calendar from event.attendees")
            return Workspace(null, "placeholder", "placeholder", listOf(), null)
        }
        return calendarIdsRepository.findWorkspaceById(calendarId) //may return placeholder
    }

    /**
     * Converts regular workspace [Booking] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application.
     * [Event.summary] is used to indicate the booking workspace.
     *
     * @param model [Booking] to be converted
     * @return The resulting [Event] object
     * @author Daniil Zavyalov
     */
    fun toGoogleWorkspaceRegularEvent(model: Booking): Event {
        logger.debug("[toGoogleWorkspaceRegularEvent] converting regular workspace booking to calendar event")
        if (model.owner == null) {
            throw UnavailableOperationException("[toGoogleWorkspaceRegularEvent] Cannot create regular event without organizer.")
        }
        val event = Event().apply {
            id = model.id
            summary = eventSummaryForRegularBooking(model)
            description = eventDescriptionRegularBooking(model)
            attendees = listOf()
            recurrence = getRecurrenceFromRecurrenceModel(model)
            start = model.beginBooking.toGoogleEventDateTime()
            end = model.endBooking.toGoogleEventDateTime()
        }
        logger.trace("[toGoogleWorkspaceEvent] {}", event)
        return event
    }

    private fun eventSummaryForRegularBooking(model: Booking): String {
        return "${model.workspace.id} - workspace id (${model.workspace.zone} - ${model.workspace.name})"
    }

    private fun eventDescriptionRegularBooking(model: Booking): String {
        val owner = model.owner ?: return ""
        return "${owner.id} - organizer id [ ${owner.email} ]"
    }

    /**
     * Converts meeting workspace [Booking] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application.
     * [Event.summary] is used to indicate the booking workspace.
     *
     * @param model [Booking] to be converted
     * @return The resulting [Event] object
     * @author Daniil Zavyalov
     */
    fun toGoogleWorkspaceMeetingEvent(model: Booking): Event {
        logger.debug("[toGoogleWorkspaceMeetingEvent] converting meeting room booking to calendar event")
        val attendeeList: MutableList<EventAttendee> = participantsAndOwnerToAttendees(model)

        val event = Event().apply {
            id = model.id
            summary = eventSummaryForMeetingBooking(model)
            description = eventDescriptionMeetingBooking(model)
            organizer =  userModelToGoogleOrganizer(model.owner)
            attendees = attendeeList
            recurrence = getRecurrenceFromRecurrenceModel(model)
            start = model.beginBooking.toGoogleEventDateTime()
            end = model.endBooking.toGoogleEventDateTime()
        }
        logger.debug("[toGoogleWorkspaceEvent] converting workspace booking model to calendar event")
        return event
    }

    private fun eventSummaryForMeetingBooking(model: Booking): String {
        return "Meet ${ model.owner?.fullName ?: "" }"
    }

    private fun eventDescriptionMeetingBooking(model: Booking): String {
        val ownerEmail = model.owner?.email ?: return ""
        return "$ownerEmail - почта организатора"
    }

    private fun getRecurrenceFromRecurrenceModel(model: Booking): List<String>? {
        val res = mutableListOf<String>()
        model.recurrence?.let{ recurrenceModel->
            res.add(recurrenceModel.toRecurrence().rule())
        }

        if (res.isEmpty()) return null
        return res
    }

    private fun participantsAndOwnerToAttendees(model: Booking): MutableList<EventAttendee> {
        val attendees: MutableList<EventAttendee> = model.participants
            .map { userModelToAttendee(it) }
            .toMutableList()

        model.owner?.let { owner ->
            val ownerAsAttendee = userModelToAttendee(owner)
            ownerAsAttendee.organizer = true
            attendees.add(ownerAsAttendee)
        }
        attendees.add(workspaceModelToAttendee(model.workspace))
        return attendees
    }

    /**
     * Converts [EventDateTime] to [Instant]. Returns placeholder if [googleDateTime] is null
     *
     * @return [Instant]
     */
    private fun toLocalInstant(googleDateTime: EventDateTime?) : Instant {
        val gmtEpoch: Long? = googleDateTime?.dateTime?.value
        val localEpoch = gmtEpoch?.let { it + BookingConstants.DEFAULT_TIMEZONE_OFFSET_MILLIS } ?: 0
        return Instant.ofEpochMilli(localEpoch)
    }

    /**
     * Converts [Instant] to [EventDateTime]
     *
     * @return [EventDateTime]
     */
    private fun Instant.toGoogleEventDateTime() : EventDateTime {
        val googleEventDateTime = EventDateTime()
        googleEventDateTime.dateTime = this.toEpochMilli().toGoogleDateTime()
        googleEventDateTime.timeZone = BookingConstants.DEFAULT_TIMEZONE_ID
        return googleEventDateTime
    }

    private fun userModelToAttendee(model: UserModel): EventAttendee {
        return EventAttendee().also {
            it.email = model.email
            it.resource = false
        }
    }

    private fun workspaceModelToAttendee(workspace:Workspace): EventAttendee {
        return EventAttendee().also {
            it.email = getCalendarIdByWorkspaceId(workspace.id.toString())
            it.resource = true
        }
    }

    /**
     * Converts [UserModel] of owner to [Organizer]
     *
     * @return [Organizer]
     * @author Danil Kiselev
     */
    private fun userModelToGoogleOrganizer(model: UserModel?): Organizer {
        if (model == null) {
            return Organizer()
        }
        return Organizer().also {
            it.email = model.email
        }
    }

    /**
     * Finds workspace calendar id by workspace id
     *
     * @param id workspace id. Should be valid UUID
     * @return calendar id by workspace id in database
     * @author Danil Kiselev, Max Mishenko
     */
    private fun getCalendarIdByWorkspaceId(id: String): String {
        return calendarIdsRepository.findByWorkspace(verifier.uuidFromString(id))
    }
}