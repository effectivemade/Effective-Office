package band.effective.office.tvServer.repository.message

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message

class FirebaseMessageRepository(
    private val firebase: FirebaseMessaging,
    private val topicName: String
) : MessageRepository {
    override fun sendMessage(messageText: String) {
        Message.builder()
            .setTopic(topicName)
            .putData("message", messageText).build()
            .also { message ->
                firebase.send(message)
            }
    }
}