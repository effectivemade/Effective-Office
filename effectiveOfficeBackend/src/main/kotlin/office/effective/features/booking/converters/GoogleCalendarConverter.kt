package office.effective.features.booking.converters

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Event.Organizer
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import office.effective.model.Recurrence.Companion.toRecurrence
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
    private val workspaceConverter: WorkspaceFacadeConverter,
    private val userConverter: UserDTOModelConverter,
    private val bookingConverter: BookingFacadeConverter,
    private val verifier: UuidValidator,
    private val workspaceRepository: WorkspaceRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val defaultAccount: String = BookingConstants.DEFAULT_CALENDAR

    /**
     * Converts [Event] to [BookingDTO]
     *
     * Creates placeholders if workspace, owner or participant doesn't exist in database
     *
     * @param event [Event] to be converted
     * @return The resulting [BookingDTO] object
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    fun toBookingDTO(event: Event): BookingDTO {
        logger.debug("[toBookingDTO] converting an event to meeting room booking dto")
        val organizer: String = event.organizer?.email ?: ""
        val email = if (organizer != defaultAccount) {
            logger.trace("[toBookingDTO] organizer email derived from event.organizer field")
            organizer
        } else {
            logger.trace("[toBookingDTO] organizer email derived from event description")
            event.description?.substringBefore(" ") ?: ""
        }
        val recurrence = event.recurrence?.toString()?.getRecurrence()?.toDto()
        val dto = BookingDTO(
            owner = getUserDto(email),
            participants = getParticipantsDto(event),
            workspace = getWorkspaceDto(getCalendarId(event)),
            id = event.id ?: null,
            beginBooking = event.start?.dateTime?.value ?: 0,
            endBooking = event.end?.dateTime?.value ?: ((event.start?.dateTime?.value ?: 0) + 86400000),
            recurrence = recurrence
        )
        logger.trace("[toBookingDTO] {}", dto.toString())
        return dto
    }

    /**
     * Gets the list of event participants, excluding resources, and returns a list of user DTOs.
     *
     * @param event The event for which participants need to be retrieved.
     * @return List of user DTOs.
     */
    private fun getParticipantsDto(event: Event): List<UserDTO> {
        val attendees = event.attendees
            .filter { attendee -> !attendee.isResource }
            .map { attendee -> attendee.email }
        return getAllUserDto(attendees)
    }

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
     * Retrieves a list of users by email addresses and converts them to a list of user DTOs.
     *
     * @param emails List of user email addresses.
     * @return List of user DTOs.
     */
    private fun getAllUserDto(emails: List<String>): List<UserDTO> {
        return userRepository
            .findAllByEmail(emails)
            .map { userModel -> userConverter.modelToDTO(userModel) }
    }

    /**
     * Retrieves a list of users by email addresses and converts them to a list of user Models.
     *
     * @param emails List of user email addresses.
     * @return List of user Models.
     */
    private fun getAllUserModels(emails: List<String>): List<UserModel> {
        return userRepository
            .findAllByEmail(emails)
    }

    /**
     * Retrieves the calendar ID of the workspace from the event.
     * If the ID is not found, returns a default value with a warning log.
     *
     * @param event The event from which to retrieve the calendar ID.
     * @return Calendar ID of the workspace or default value.
     */
    private fun getCalendarId(event: Event): String {
        return event.attendees?.firstOrNull { it?.resource ?: false }?.email
            ?: run {
                logger.warn("[toBookingDTO] can't get workspace calendar from event.attendees")
                "c_1882249i0l5ieh0cih42dli6fodbi@resource.calendar.google.com"
            }  //TODO: Think about a different behavior in case of null
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
    private fun getWorkspaceDto(calendarId: String): WorkspaceDTO {
        val workspaceModel: Workspace = calendarIdsRepository.findWorkspaceById(calendarId) //may return placeholder
        return workspaceConverter.modelToDto(workspaceModel)
    }

    /**
     * Find [WorkspaceDTO] by workspace calendar id
     *
     * @param calendarId Google id of calendar of workspace
     * @return [WorkspaceDTO]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun getWorkspaceModel(calendarId: String): Workspace {
        return calendarIdsRepository.findWorkspaceById(calendarId) //may return placeholder
    }

    /**
     * Find [UserDTO] by email. May return placeholder if user with the given email doesn't exist in database
     *
     * @param email
     * @return [UserDTO] with data from database or [UserDTO] placeholder with the given [email]
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    private fun getUserDto(email: String): UserDTO {
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
        return userConverter.modelToDTO(userModel)
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
     * Find [UserDTO] by email. May return placeholder if user with the given email doesn't exist in database
     *
     * @param email
     * @return [UserDTO] with data from database or [UserDTO] placeholder with the given [email]
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
//    private fun getAllUser(emails: List<String>): UserDTO {
//        val userModel: UserModel = userRepository.findByEmail(email)
//            ?: run {
//                logger.warn("[getUser] can't find a user with email ${email}. Creating placeholder.")
//                UserModel(
//                    id = null,
//                    fullName = "Unregistered user",
//                    tag = null,
//                    active = false,
//                    role = null,
//                    avatarURL = null,
//                    integrations = emptySet(),
//                    email = email
//                )
//            }
//        return userConverter.modelToDTO(userModel)
//    }

    /**
     * Converts [BookingDTO] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application
     *
     * @param dto [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Danil Kiselev, Max Mishenko
     */
    fun toGoogleEvent(dto: BookingDTO): Event {
        logger.debug("[toGoogleEvent] converting meeting room booking dto to calendar event")
        val event = Event().apply {
            summary = createDetailedEventSummary(dto)
            description = "${dto.owner.email} - почта организатора"
            organizer = dto.owner.toGoogleOrganizer()
            attendees = dto.participants.map { it.toAttendee() } + dto.owner.toAttendee()
                .apply { organizer = true } + dto.workspace.toAttendee()
            if (dto.recurrence != null) recurrence = listOf(dto.recurrence.toRecurrence().rule())
            start = dto.beginBooking.toGoogleDateTime()
            end = dto.endBooking.toGoogleDateTime()
        }
        logger.trace("[toGoogleEvent] {}", event.toString())
        return event
    }

    /**
     * Converts regular workspace [BookingDTO] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application.
     * [Event.summary] is used to indicate the booking workspace.
     *
     * @param model [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Daniil Zavyalov
     */
    fun toGoogleWorkspaceRegularEvent(model: Booking): Event {
        logger.debug("[toGoogleWorkspaceEvent] converting workspace booking dto to calendar event")
        val event = Event().apply {
            summary = "${model.workspace.id} - workspace id"
            description = "${model.owner.id} - organizer id"
            getRecurrenceFromRecurrenceModel(model)?.let{ recurrence = it }
            start = model.beginBooking.toGoogleDateTime()
            end = model.endBooking.toGoogleDateTime()
        }
        logger.trace("[toGoogleWorkspaceEvent] {}", event.toString())
        return event
    }

    /**
     * Converts meeting workspace [Booking] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application.
     * [Event.summary] is used to indicate the booking workspace.
     *
     * @param model [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Daniil Zavyalov
     */
    fun toGoogleWorkspaceMeetingEvent(model: Booking): Event {
        val event = Event().apply {
            summary = createDetailedEventSummary(model)
            description = "${model.owner.email} - почта организатора"
            organizer =  userModelToGoogleOrganizer(model.owner)
            attendees = model.participants.map { userModelToAttendee(it) } + userModelToAttendee(model.owner)
                .apply { organizer = true } + workspaceModelToAttendee(model.workspace)
            getRecurrenceFromRecurrenceModel(model)?.let{ recurrence = it }
            start = model.beginBooking.toGoogleDateTime()
            end = model.endBooking.toGoogleDateTime()
        }
        logger.debug("[toGoogleWorkspaceEvent] converting workspace booking model to calendar event")
        return event;
//        return toGoogleWorkspaceEvent(bookingConverter.modelToDto(model))
    }

    private fun getRecurrenceFromRecurrenceModel(model: Booking): List<String>? {
//        return listOf(model.recurrence?.toRecurrence()?.rule())?: return null;
        val res = mutableListOf<String>()
        model.recurrence?.let{ recurrenceModel->
            res.add(recurrenceModel.toRecurrence().rule())
        }

        if (res.isEmpty()) return null
        return res
    }



//    /**
//     * Converts meeting room [Booking] to [Event]. [Event.description] is used to indicate the booking author,
//     * because [Event.organizer] is [defaultAccount] of application
//     *
//     * @param model [BookingDTO] to be converted
//     * @return The resulting [Event] object
//     * @author Danil Kiselev
//     */
//    fun toGoogleWorkspaceMeetingEvent(model: Booking): Event {
//        logger.debug("[toGoogleEvent] converting meeting room booking model to calendar event")
//        return toGoogleEvent(bookingConverter.modelToDto(model))
//    }

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
            beginBooking = Instant.ofEpochMilli(event.start?.dateTime?.value?:0) ,
            endBooking = Instant.ofEpochMilli(event.end?.dateTime?.value ?: ((event.start?.dateTime?.value ?: 0) + 86400000)),
            recurrence = recurrence?.let{ RecurrenceConverter.recurrenceToModel(it) }
        )
        logger.trace("[toBookingModel] {}", booking.toString())
        return booking

    }

    private fun createDetailedEventSummary(dto: BookingDTO): String {
        return "Meet ${dto.owner.fullName}"
    }
    private fun createDetailedEventSummary(model: Booking): String {
        return "Meet ${model.owner.fullName}"
    }

    /**
     * Converts milliseconds to [EventDateTime]
     *
     * @return [EventDateTime]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun Long.toGoogleDateTime(): EventDateTime {
        return EventDateTime().also {
            it.dateTime = DateTime(this - TimeZone.getDefault().rawOffset)
            it.timeZone = TimeZone.getDefault().id
        }
    }

    private fun Instant.toGoogleDateTime():EventDateTime {
        return EventDateTime().also {
            it.dateTime = DateTime(this.toEpochMilli() - TimeZone.getDefault().rawOffset)
            it.timeZone = TimeZone.getDefault().id
        }
    }


    /**
     * Converts [UserDTO] of participant to [EventAttendee]
     *
     * @return [EventAttendee]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun UserDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email =
                this.email// this.integrations?.first { it.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    private fun userDtoToAttendee(dto: UserDTO): EventAttendee {
        return EventAttendee().also {
            it.email = dto.email
            it.resource = false
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
            it.email = getCalendarIdByWorkspaceId(workspace.id.toString()) //TODO надо допилить получение комнаты
            it.resource = true
        }
    }

    /**
     * Converts [UserDTO] of owner to [Organizer]
     *
     * @return [Organizer]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun UserDTO.toGoogleOrganizer(): Organizer? {
        return Organizer().also {
            it.email =
                this.email//this.integrations?.first { integ -> integ.name == "email" }?.value //TODO надо допилить получение почты
            //It doesn't work. Google ignores event organizer. Event organizer will be the calendar itself.
        }
    }

    /**
     * Converts [UserDTO] of owner to [Organizer]
     *
     * @return [Organizer]
     * @author Danil Kiselev
     */
    private fun userDTOtoGoogleOrganizer(dto: UserDTO): Organizer {
        return Organizer().also {
            it.email = dto.email
        }
    }

    /**
     * Converts [UserModel] of owner to [Organizer]
     *
     * @return [Organizer]
     * @author Danil Kiselev
     */
    private fun userModelToGoogleOrganizer(model: UserModel): Organizer {
        return Organizer().also {
            it.email = model.email
        }
    }

    /**
     * Converts [WorkspaceDTO]to [EventAttendee] (Rooms are also considered participants)
     *
     * @return [EventAttendee]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun WorkspaceDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email = getCalendarIdByWorkspaceId(id) //TODO надо допилить получение комнаты
            it.resource = true
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