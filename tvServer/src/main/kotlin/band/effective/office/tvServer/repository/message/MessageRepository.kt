package band.effective.office.tvServer.repository.message

interface MessageRepository {
    fun sendMessage(messageText: String)
}