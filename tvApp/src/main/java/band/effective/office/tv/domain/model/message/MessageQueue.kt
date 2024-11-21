package band.effective.office.tv.domain.model.message

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**UpdatingQueue with observer pattern*/
class MessageQueue {
    val queue: MutableStateFlow<UpdatingQueue> = MutableStateFlow(UpdatingQueue())
    fun push(message: BotMessage) = queue.update { it.push(message = message) }
    fun pop() = queue.update {
        BotMessage.safeMessage(it.top())
        it.pop()
    }
    fun top(): BotMessage = queue.value.top()
    fun isNotEmpty() = queue.value.queue.isNotEmpty()
    fun removeMessage(message: BotMessage) = queue.update { it.copy(queue = it.queue - message) }
    fun message(id: String) = queue.value.queue.firstOrNull { it.id == id }

    fun contain(id: String): Boolean = message(id) != null

    companion object {
        /**Queue for important messages*/
        val firstQueue = MessageQueue()
        /**Queue for unimportant messages*/
        val secondQueue = MessageQueue()
    }
}