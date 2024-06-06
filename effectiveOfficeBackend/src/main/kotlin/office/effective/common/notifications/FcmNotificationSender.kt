package office.effective.common.notifications

import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import office.effective.common.constants.BookingConstants
import office.effective.dto.*
import office.effective.features.calendar.repository.CalendarIdsRepository
import office.effective.model.Booking
import office.effective.model.Workspace
import office.effective.serviceapi.IBookingService
import org.slf4j.LoggerFactory
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

/**
 * Class for sending Firebase cloud messages
 */
class FcmNotificationSender(
    private val fcm: FirebaseMessaging,
    private val calendarIdsRepository: CalendarIdsRepository,
    private val bookingService: IBookingService,
    private val fcmWorkspaceConverter: FcmWorkspaceWithBookingsDTOModelConverter
): INotificationSender {
    private val logger = LoggerFactory.getLogger(FcmNotificationSender::class.java)

    companion object {
        private const val AVG_BOOKING_EVENT_SIZE = 260
        // 296 bytes for default structure (token, message) and message id
        private const val MAX_MESSAGE_SIZE = 3800
        const val MAX_BOOKING_EVENTS_IN_MESSAGE = MAX_MESSAGE_SIZE / AVG_BOOKING_EVENT_SIZE
    }
    /**
     * Sends empty FCM message on topic
     *
     * @author Daniil Zavyalov
     */
    override fun sendEmptyMessage(topic: String) {
        logger.info("Sending an FCM message on $topic topic")
        val msg: Message = Message.builder()
            .setTopic(topic)
            .putData("message", "$topic was changed")
            .build()
        fcm.send(msg)
    }
    
    override fun sendUpdateContentMessages(topic: String, resourceId: String) {
        logger.info("[sendUpdateContentMessages] Received update on {} calendar id", resourceId)
        val workspace = calendarIdsRepository
            .findWorkspaceById(resourceId, shouldFindUtilities = false)
        if (workspace.tag != "meeting") return

        val startTime = ZonedDateTime
            .now(ZoneId.of(BookingConstants.DEFAULT_TIMEZONE_ID))
        val endTime = startTime
            .with(LocalTime.MAX)
            .plusDays(7)
        
        val startTimeMs = startTime.toEpochSecond() * 1000
        val endTimeMs = endTime.toEpochSecond() * 1000
        
        val bookings = bookingService.findAll(
            workspaceId = workspace.id,
            bookingRangeFrom = startTimeMs,
            bookingRangeTo = endTimeMs,
            shouldFindIntegrationsAndUtilities = false
        )
        
        val messages = buildMessagesList(workspace, bookings, topic)
        val batchResponse = fcm.sendEach(messages)
        handleFcmResponses(batchResponse)
    }
    
    private fun buildMessagesList(workspace: Workspace, bookings: List<Booking>, topic: String): List<Message> {
        val messageId = UUID.randomUUID().toString()
        if (bookings.isEmpty()) {
            val message = buildMessage(messageId, workspace, emptyList(), topic, "1", "1")
            return listOf(message)
        }
        
        val bookingChunks = bookings.chunked(MAX_BOOKING_EVENTS_IN_MESSAGE)
        val messages = bookingChunks.mapIndexed { index, bookingList ->
            buildMessage(
                messageId, workspace, bookingList, topic,
                index.toString(), bookingChunks.size.toString()
            )
        }
        
        return messages
    }
    
    private fun buildMessage(
        messageId: String,
        workspace: Workspace,
        bookingList: List<Booking>,
        topic: String,
        messageNum: String,
        maxMessages: String
    ): Message {
        val fcmWorkspace = fcmWorkspaceConverter
            .fromModelsToDTO(workspace, bookingList)
        val json = Json.encodeToString(fcmWorkspace)

        return Message.builder()
            .setTopic(topic)
            .putData("id", messageId)
            .putData("workspace", json)
            .putData("messageNum", messageNum)
            .putData("maxMessages", maxMessages)
            .build()
    }
    
    
    private fun handleFcmResponses(batchResponse: BatchResponse) {
        for (response in batchResponse.responses) {
            if (!response.isSuccessful) {
                logger.error("[handleFcmResponses] Message was not successfully delivered, exception: {}, error code: {}",
                    response.exception.message, response.exception.errorCode
                )
            }
        }
        logger.info("[handleFcmResponses] {} messages have been sent, {} of them successful, {} - failures",
            batchResponse.responses.size, batchResponse.successCount, batchResponse.failureCount
        )
    }

    /**
     * Sends an FCM message about workspace modification. Uses "workspace" topic
     *
     * @param action will be put as "action" in message data
     * @param modifiedWorkspace will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    override fun sendContentMessage(action: HttpMethod, modifiedWorkspace: WorkspaceDTO) {
        logger.info("Sending an FCM message on workspace topic")
        val json = Json.encodeToString(modifiedWorkspace)
        val msg: Message = Message.builder()
            .setTopic("workspace")
            .putData("action", action.value)
            .putData("object", json)
            .build()
        fcm.send(msg)
    }

    /**
     * Sends an FCM message about user modification. Uses "user" topic
     *
     * @param action will be put as "action" in message data
     * @param modifiedUser will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    override fun sendContentMessage(action: HttpMethod, modifiedUser: UserDTO) {
        logger.info("Sending an FCM message on user topic")
        val json = Json.encodeToString(modifiedUser)
        val msg: Message = Message.builder()
            .setTopic("user")
            .putData("action", action.value)
            .putData("object", json)
            .build()
        fcm.send(msg)
    }

    /**
     * Sends an FCM message about booking modification. Uses "booking" topic
     *
     * @param action will be put as "action" in message data
     * @param modifiedBooking will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    override fun sendContentMessage(action: HttpMethod, modifiedBooking: BookingDTO) {
        logger.info("Sending an FCM message on booking topic")
        val json = Json.encodeToString(modifiedBooking)
        val msg: Message = Message.builder()
            .setTopic("booking")
            .putData("action", action.value)
            .putData("object", json)
            .build()
        fcm.send(msg)
    }
}