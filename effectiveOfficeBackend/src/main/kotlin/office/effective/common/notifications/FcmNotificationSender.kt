package office.effective.common.notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO
import org.slf4j.LoggerFactory

/**
 * Class for sending Firebase cloud messages
 */
class FcmNotificationSender(private val fcm: FirebaseMessaging): INotificationSender {
    private val logger = LoggerFactory.getLogger(FcmNotificationSender::class.java)

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