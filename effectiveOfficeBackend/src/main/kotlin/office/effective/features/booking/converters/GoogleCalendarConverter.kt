package office.effective.features.booking.converters

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Event.Organizer
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import office.effective.common.constants.BookingConstants
import office.effective.common.utils.UuidValidator
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO
import office.effective.features.calendar.repository.CalendarIdsRepository
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
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
     * Gets the list of event participants, excluding resources, and returns a list of user Models.
     *
     * @param event The event for which participants need to be retrieved.
     * @return List of user Models.
     */
    private fun getParticipantsModels(event: Event): List<UserModel> {
        val attendees = event.attendees
            .filter { attendee -> !attendee.isResource }
            .map { attendee -> attendee.email }
        return getAllUserModels(attendees)
    }

    /**
     * Retrieves a list of users by email addresses and converts them to a list of user Models.
     *
     * @param emails List of user email addresses.
     * @return List of user Models.
     */
    private fun getAllUserModels(emails: List<String>): List<UserModel> {
        return userRepository.findAllByEmails(emails)
    }

    /**
     * Retrieves the calendar ID of the workspace from the event.
     * If the ID is not found, returns a default value with a warning log.
     *
     * @param event The event from which to retrieve the calendar ID.
     * @return Calendar ID of the workspace or default value.
     */
    private fun getCalendarId(event: Event): String? {
        return event.attendees?.firstOrNull { it?.resource ?: false }
            ?.email
    }

    /**
     * Converts regular [Event] to [Booking]
     *
     * Creates placeholders if workspace or owner doesn't exist in database
     *
     * @param event [Event] to be converted
     * @return The resulting [Booking] object
     * @throws Exception if it fails to get user id from description or workspace id from summary of Google Calendar event
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    fun toWorkspaceBooking(event: Event): Booking {
        logger.debug("[toWorkspaceBooking] converting an event to workspace booking dto")
        val userId: UUID = try {
            UUID.fromString(event.description.substringBefore(" "))
        } catch (e: Exception) {
            throw Exception("Can't get user UUID from Google Calendar event description. Reason: ${e.printStackTrace()}")
        }
        val workspaceID: UUID = try {
            UUID.fromString(event.summary.substringBefore(" "))
        } catch (e: Exception) {
            throw Exception("Can't get user UUID from Google Calendar event summary. Reason: ${e.printStackTrace()}")
        }
        val recurrence = event.recurrence?.toString()?.getRecurrence()

        val model = Booking(
            owner = userRepository.findById(userId)
                ?: run {
                    logger.warn("[toWorkspaceBooking] can't find user with id ${userId}. Creating placeholder.")
                    UserModel(
                        id = null,
                        fullName = "Nonexistent user",
                        tag = null,
                        active = false,
                        role = null,
                        avatarURL = null,
                        integrations = emptySet(),
                        email = ""
                    )
                },
            participants = emptyList(),
            workspace = workspaceRepository.findById(workspaceID)
                ?:  run {
                    logger.warn("[toWorkspaceBooking] can't find a user with id ${userId}. Creating placeholder.")
                    Workspace(null, "Nonexistent workspace", "placeholder", listOf(), null)
                },
            id = event.id ?: null,
            beginBooking = Instant.ofEpochMilli(event.start?.dateTime?.value ?: 0),
            endBooking = Instant.ofEpochMilli(
                event.end?.dateTime?.value ?: ((event.start?.dateTime?.value ?: 0) + 86400000)
            ),
            recurrence = recurrence?.toDto()?.let {
                RecurrenceConverter.recurrenceToModel(recurrence)
            }
        )
        logger.trace("[toBookingDTO] {}", model.toString())
        return model
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
     * Find [UserModel] by email. May return placeholder if user with the given email doesn't exist in database
     *
     * @param email
     * @return [UserDTO] with data from database or [UserDTO] placeholder with the given [email]
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    private fun getUserModel(email: String): UserModel {
        val userModel: UserModel = userRepository.findByEmail(email)
            ?: run {
                logger.warn("[getUser] can't find a user with email ${email}. Creating placeholder.")
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
        val attendeeList: MutableList<EventAttendee> = participantsAndOwnerToAttendees(model)

        val event = Event().apply {
            id = model.id
            summary = eventSummaryForRegularBooking(model)
            description = eventDescriptionRegularBooking(model)
            attendees = attendeeList
            recurrence = getRecurrenceFromRecurrenceModel(model)
            start = model.beginBooking.toGoogleDateTime()
            end = model.endBooking.toGoogleDateTime()
        }
        logger.trace("[toGoogleWorkspaceEvent] {}", event.toString())
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
        attendeeList.add(workspaceModelToAttendee(model.workspace))

        val event = Event().apply {
            id = model.id
            summary = eventSummaryForMeetingBooking(model)
            description = eventDescriptionMeetingBooking(model)
            organizer =  userModelToGoogleOrganizer(model.owner)
            attendees = attendeeList
            recurrence = getRecurrenceFromRecurrenceModel(model)
            start = model.beginBooking.toGoogleDateTime()
            end = model.endBooking.toGoogleDateTime()
        }
        logger.debug("[toGoogleWorkspaceEvent] converting workspace booking model to calendar event")
        return event;
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
        return attendees
    }

    /**
     * Converts meeting [Event] to [Booking]
     *
     *
     * @param event [Event] to be converted
     * @return The resulting [BookingDTO] object
     * @author Danil Kiselev, Max Mishenko
     */
    fun toBookingModelForMeetingWorkspace(event: Event): Booking {
        logger.debug("[toGoogleEvent] converting calendar event to meeting room booking model")
        val organizer: String = event.organizer?.email ?: ""
        val email = if (organizer != defaultAccount) {
            logger.trace("[toBookingModel] organizer email derived from event.organizer field")
            organizer
        } else {
            logger.trace("[toBookingModel] organizer email derived from event description")
            event.description?.substringBefore(" ") ?: ""
        }
        val recurrence = event.recurrence?.toString()?.getRecurrence()

        val booking = Booking(
            owner = getUserModel(email),
            participants = getParticipantsModels(event),
            workspace = getWorkspaceModel(getCalendarId(event)),
            id = event.id ?: null,
            beginBooking = Instant.ofEpochMilli(event.start?.dateTime?.value ?: 0),
            endBooking = Instant.ofEpochMilli(event.end?.dateTime?.value ?: 1),
            recurrence = recurrence?.let { RecurrenceConverter.recurrenceToModel(it) }
        )
        logger.trace("[toBookingModel] {}", booking.toString())
        return booking

    }

    /**
     * Converts [Instant] to [EventDateTime]
     *
     * @return [EventDateTime]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun Instant.toGoogleDateTime():EventDateTime {
        return EventDateTime().also {
            it.dateTime = DateTime(this.toEpochMilli())
            it.timeZone = BookingConstants.DEFAULT_TIMEZONE_ID
        }
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